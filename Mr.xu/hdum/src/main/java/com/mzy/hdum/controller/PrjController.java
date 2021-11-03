package com.mzy.hdum.controller;

import com.mzy.hdum.model.Prj;
import com.mzy.hdum.service.PrjService;
import com.mzy.hdum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/prj")
public class PrjController {

    @Autowired
    private PrjService prjService;

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Integer delete(Integer id) {
        System.out.println(id);
        int result = prjService.delete(id);
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(Prj prj) {
        int result = prjService.Update(prj);
        if (result >= 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }

    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Prj insert(Prj prj) {
        return prjService.insertPrj(prj);
    }

    @RequestMapping("/ListPrj")
    @ResponseBody
    public List<Prj> ListPrj() {
        return prjService.ListPrj();
    }

    @RequestMapping("/ListByName")
    @ResponseBody
    public List<Prj> ListPrjByName(String prjname) {
        return prjService.findByName(prjname);
    }

    /**
     * 分页
     * @return
     */
    @RequestMapping(value="/page")
    @ResponseBody
    public List<Prj> page(Integer page){
        int pageNow = page == null ? 1 : page;
        int pageSize = 9;
        int startRows = pageSize*(pageNow-1);
        List<Prj> list = prjService.queryPage(startRows);
        return list;
    }

    /**
     * rows
     * @return
     */
    @RequestMapping(value="/rows")
    @ResponseBody
    public int rows(){
        return prjService.getRowCount();
    }

    @RequestMapping(value = "/findState", method = RequestMethod.POST)
    @ResponseBody
    public List<String> findState() {
        return prjService.findState();
    }

    @RequestMapping(value = "/findByState", method = RequestMethod.POST)
    @ResponseBody
    public List<Prj> findByState(String state, String prjname) {
        if(state.equals(""))
            return prjService.findByName(prjname);
        else
            return prjService.findByState(state, prjname);
    }

    @RequestMapping(value = "/addStar", method = RequestMethod.POST)
    @ResponseBody
    public String addStar(Integer id){
        int result = prjService.addStar(id);
        if (result >= 1) {
            return "点赞成功";
        } else {
            return "点赞失败";
        }
    }

    @RequestMapping(value = "/addClick", method = RequestMethod.POST)
    @ResponseBody
    public int addClick(Integer id) {
        return prjService.addClick(id);
    }
}

