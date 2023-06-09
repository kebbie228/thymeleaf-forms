package smart;

import org.itstep.firm.Firm;
import org.itstep.firm.FirmService;
import org.itstep.os.Os;
import org.itstep.os.OsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@Controller
public class SmartController {
    @Autowired
    SmartService smartService;
    @Autowired
    FirmService firmService;
    @Autowired
    OsService osService;



    @GetMapping(value = "/smarts")
    public String firms(Model model) {
        model.addAttribute("smarts", smartService.findAll());
        return "smarts";
    }


    @GetMapping(value = "/smart_add")
    public String smartAdd(Model model) {
        model.addAttribute("smart", new Smart());
        model.addAttribute("firms", firmService.findAll());
        model.addAttribute("oss", osService.findAll());
        return "smart_add";
    }

    @PostMapping(value = "/os_add")
    public String firmSave(Os os, Model model, HttpServletResponse response) {
        Os newOs = osService.save(os);
        long id = newOs.getId();
        response.addHeader("id", String.valueOf(id));
        model.addAttribute("oss", osService.findAll());
        return "redirect:/oss";
    }

    @DeleteMapping(value = "/os_delete")
    public String firmDelete(@RequestParam(name = "id") Long id) {
        osService.deleteById(id);
        return "redirect:/os";
    }

    @GetMapping(value = "/os_update")
    public String firmGetUpdate(Model model, @RequestParam(name = "id") Long id) {
        Os oldOs = osService.findById(id);
        model.addAttribute("os", oldOs);
        return "os_update";
    }

    @PutMapping(value = "/os_update")
    public String firmUpdate(Os os, Model model) {
        Os oldOs = osService.findById(os.getId());
        oldOs.setName(os.getName());
        oldOs.setDeveloper(os.getDeveloper());
        osService.save(oldOs);
        model.addAttribute("oss", osService.findAll());
        return "redirect:/oss";
    }
}

