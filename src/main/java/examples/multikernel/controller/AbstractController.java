package examples.multikernel.controller;

import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Created by pengyu.
 *
 * @author pengyu
 */
public abstract class AbstractController {

    protected final Cookie[] defaultCookies = new Cookie[]{
            newCookie("cookie0", "value0", true, true, null),
            newCookie("cookie1", "value1", true, true, "/"),
            newCookie("cookie2", "value2", true, true, "/app"),
            newCookie("cookie3", "value3", true, true, "/app2"),
            newCookie("cookie4", "value4", true, false, null),
            newCookie("cookie5", "value5", true, false, "/"),
            newCookie("cookie6", "value6", true, false, "/app"),
            newCookie("cookie7", "value7", true, false, "/app2"),
            newCookie("cookie8", "value8", false, true, null),
            newCookie("cookie9", "value9", false, true, "/"),
            newCookie("cookie10", "value10", false, true, "/app"),
            newCookie("cookie11", "value11", false, true, "/app2"),
            newCookie("cookie12", "value12", false, false, null),
            newCookie("cookie13", "value13", false, false, "/"),
            newCookie("cookie14", "value14", false, false, "/app"),
            newCookie("cookie15", "value15", false, false, "/app2")
    };

    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("defaultCookies", defaultCookies);
        return "/index";
    }

    public String addCookies(HttpServletRequest request, HttpServletResponse response, Model model) {
        for (Cookie cookie : defaultCookies) {
            response.addCookie(cookie);
        }
        model.addAttribute("defaultCookies", defaultCookies);
        return "/addCookies";
    }

    public String checkCookies(HttpServletRequest request, HttpServletResponse response, Model model) {
        final Cookie[] cookiesFromRequest = request.getCookies();
        model.addAttribute("defaultCookies", defaultCookies);
        model.addAttribute("requestCookies", cookiesFromRequest);
        final Cookie[] lostCookies = Arrays.stream(defaultCookies).filter(cookie -> !contains(cookie.getName(), cookiesFromRequest)).toArray(Cookie[]::new);
        model.addAttribute("lostCookies", lostCookies);
        if (cookiesFromRequest == null) {
            return "checkCookies";
        }
        final Cookie[] cookiesInDefault = Arrays.stream(cookiesFromRequest).filter(cookie -> contains(cookie.getName(), defaultCookies)).toArray(Cookie[]::new);
        final Cookie[] cookiesNotInDefault = Arrays.stream(cookiesFromRequest).filter(cookie -> !contains(cookie.getName(), defaultCookies)).toArray(Cookie[]::new);
        model.addAttribute("cookiesInDefault", cookiesInDefault);
        model.addAttribute("cookiesNotInDefault", cookiesNotInDefault);

        return "checkCookies";
    }

    private boolean contains(final String name, final Cookie[] cookies) {
        if (cookies == null) {
            return false;
        }
        return Arrays.stream(cookies).anyMatch(cookie -> cookie.getName().equals(name));
    }

    private Cookie newCookie(String name, String value, boolean secure, boolean httpOnly, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(secure);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(3600);
        if (path != null) {
            cookie.setPath(path);
        }
        return cookie;
    }

    public String clearCookies(HttpServletRequest request, HttpServletResponse response) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "redirect:" + getServerName() + "/index";
        }
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:" + getServerName() + "/index";
    }

    protected abstract String getServerName();
}
