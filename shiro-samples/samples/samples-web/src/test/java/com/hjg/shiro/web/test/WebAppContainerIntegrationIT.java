package com.hjg.shiro.web.test;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.hjg.shiro.testing.web.AbstractContainerIT;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-18 11:57
 */
public class WebAppContainerIntegrationIT extends AbstractContainerIT {

    protected final WebClient webClient = new WebClient();

    @Before
    public void logOut() throws IOException {
        // Make sure we are logged out
        final HtmlPage homePage = webClient.getPage(getTlsBaseUri());
        try {
            homePage.getAnchorByHref("/logout").click();
        }
        catch (ElementNotFoundException e) {
            //Ignore
        }
    }

    @Test
    public void logIn() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {

        HtmlPage page = webClient.getPage(getTlsBaseUri() + "login.jsp");
        HtmlForm form = page.getFormByName("loginform");
        form.<HtmlInput>getInputByName("username").setValueAttribute("root");
        form.<HtmlInput>getInputByName("password").setValueAttribute("secret");
        page = form.<HtmlInput>getInputByName("submit").click();
        // This'll throw an expection if not logged in
        page.getAnchorByHref("/logout");
    }
}
