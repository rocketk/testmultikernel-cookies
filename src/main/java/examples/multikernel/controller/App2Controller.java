package examples.multikernel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pengyu.
 * @author pengyu
 */
@Controller("app2Controller")
public class App2Controller extends AbstractController {

    public final String serverName = "/app2";

    @Override
    @GetMapping(serverName + "/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        return super.index(request, response, model);
    }

    @Override
    @GetMapping(serverName + "/addCookies")
    public String addCookies(HttpServletRequest request, HttpServletResponse response, Model model) {
        return super.addCookies(request, response, model);
    }

    @Override
    @GetMapping(serverName + "/checkCookies")
    public String checkCookies(HttpServletRequest request, HttpServletResponse response, Model model) {
        return super.checkCookies(request, response, model);
    }

    @Override
    @GetMapping(serverName + "/clearCookies")
    public String clearCookies(HttpServletRequest request, HttpServletResponse response) {
        return super.clearCookies(request, response);
    }

    @Override
    protected String getServerName() {
        return serverName;
    }
}
