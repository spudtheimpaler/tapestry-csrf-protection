package org.apache.tapestry5.csrfprotection.util;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.csrfprotection.services.CsrfProtectionModule;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.test.PageTester;

public final class PageTesterUtils
{
    private PageTesterUtils()
    {
    }

    public static PageTester autoModePageTester()
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.auto";
        PageTester tester =
            new PageTester(appPackage, "AutoMode", "src/test/webapp",
                CsrfProtectionModule.class, org.apache.tapestry5.csrfprotection.tests.auto.services.AppModule.class,
                HttpMockModule.class);
        return tester;
    }

    public static PageTester offModePageTester()
    {
        String appPackage = "org.apache.tapestry5.csrfprotection.tests.off";
        PageTester tester =
            new PageTester(appPackage, "OffMode", "src/test/webapp",
                CsrfProtectionModule.class, org.apache.tapestry5.csrfprotection.tests.off.services.AppModule.class);
        return tester;
    }

    public static class HttpMockModule
    {
        public static RequestGlobals decorateRequestGlobals(final RequestGlobals originalRequestGlobals)
        {
            HttpServletRequest httpServletRequest = createNiceMock(HttpServletRequest.class);
            RequestGlobals requestGlobals = createNiceMock(RequestGlobals.class);

            requestGlobals.storeRequestResponse(anyObject(Request.class), anyObject(Response.class));
            expectLastCall().andDelegateTo(originalRequestGlobals);

            requestGlobals.storeActivePageName(anyObject(String.class));
            expectLastCall().andDelegateTo(originalRequestGlobals);

            expect(requestGlobals.getRequest()).andDelegateTo(originalRequestGlobals);
            expect(requestGlobals.getResponse()).andDelegateTo(originalRequestGlobals);
            expect(requestGlobals.getHTTPServletRequest()).andReturn(httpServletRequest);

            replay(requestGlobals, httpServletRequest);

            return requestGlobals;
        }
    }

}
