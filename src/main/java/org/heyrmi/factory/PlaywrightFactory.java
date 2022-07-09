package org.heyrmi.factory;

import com.microsoft.playwright.*;
import org.heyrmi.exception.WrongArgumentException;

import java.nio.file.Paths;
import java.util.Objects;

import static org.heyrmi.config.ConfigurationManager.configuration;

public class PlaywrightFactory {


    private static final ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
    private static final ThreadLocal<BrowserType> browserTypeThread = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContextThread = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThread = new ThreadLocal<>();


    //To avoid external instantiation
    private PlaywrightFactory() {}


    public static synchronized Page getPage(){
        // Can also write: playwrightThread.get() == null
        if(Objects.isNull(playwrightThread.get())){
            //If the object is null then assign the new instance
            Playwright playwright = Playwright.create();
            playwrightThread.set(playwright);

            //Make new page for this instance
            Page page = createPage(playwrightThread.get());
            pageThread.set(page);
        }
        return pageThread.get();
    }

    private static synchronized Page createPage(Playwright playwright){
        String borwserName = configuration().browser();
        BrowserType browserType = getBrowserType(playwright, borwserName);

        //Launch browser with property headless
        Browser browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(configuration().headless()));

        //For context options (Not mandatory)
        /*Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        contextOptions.acceptDownloads = true;*/

        //You can add contextOptions in this BrowserContext
        BrowserContext context = browser.newContext();
        //To save the context
        //context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("KeeepingItEmptyForNow")));


        //Set values in threadlocal variables
        browserTypeThread.set(browserType);
        browserThread.set(browser);
        browserContextThread.set(context);

        //Start Tracing
        if(configuration().tracing()){
            startTracing(context);
        }

        //return threadsafe page instance
        return context.newPage();

    }

    private static synchronized BrowserType getBrowserType(Playwright playwright, String browserName){
        switch (browserName) {
            case "chromium":
                return playwright.chromium();
            case "webkit":
                return playwright.webkit();
            case "firefox":
                return playwright.firefox();
            default:
                //TODO: Add new custom exception
                throw new WrongArgumentException("The browser you are trying to access is not supported by Playwright");
        }
    }

    /**
     * Want to start tracing?
     * For more info visit: https://playwright.dev/java/docs/api/class-tracing
     * @param context
     */
    //TODO: implement this once design is finalised
    private static synchronized void startTracing(BrowserContext context){
        // Start tracing before creating / navigating a page.
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(false));
    }


    public static synchronized void closePage(){
        Playwright playwright = playwrightThread.get();
        Page page = pageThread.get();
        // Close if only they are not null
        if(Objects.nonNull(playwright)){
            //close page
            page.close();
            pageThread.remove();

            // then close playwright
            playwright.close();
            playwrightThread.remove();
        }
    }



}
