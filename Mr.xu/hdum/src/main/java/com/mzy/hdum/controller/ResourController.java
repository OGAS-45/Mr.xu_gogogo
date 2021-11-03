package com.mzy.hdum.controller;

import com.mzy.hdum.model.Prj;
import com.mzy.hdum.model.Resour;
import com.mzy.hdum.service.PrjService;
import com.mzy.hdum.service.ResourService;
import com.mzy.hdum.util.ClsVo;
import com.mzy.hdum.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/resour")
public class ResourController {

    @Autowired
    private ResourService resourService;
    @Autowired
    private PrjService prjService;

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Integer delete(Integer id) {
        System.out.println(id);
        int result = resourService.delete(id);
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(Resour resour) {
        int result = resourService.Update(resour);
        if (result >= 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }

    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Resour insert(Resour resour) {
        return resourService.insertResour(resour);
    }

    @RequestMapping("/ListResour")
    @ResponseBody
    public List<Resour> ListResour() {
        return resourService.ListResour();
    }


    @RequestMapping("/ListResourUrlById/{id}")
    @ResponseBody
    public String ListResourUrlById(@PathVariable("id")int id){

        List<Resour> resour=resourService.findById(id);

        if (resour.size()>0)
           return resour.get(0).getUrl();
        else
         return null;
    }

    @RequestMapping("/ListResourByClsTree/{prjid}")
    @ResponseBody
    public List<ClsVo> ListResour3(@PathVariable("prjid")int prjid) {
        List<Prj> pl= prjService.findById(prjid);
        if(pl == null) return null;

        String labels=pl.get(0).getLabel();
        if(labels==null) return null;

        String[] sArray=labels.split(",") ;
        if(sArray.length==0) return null;

        List<Resour> res=new ArrayList<Resour>();
        for (int i = 0; i < sArray.length; i++) {
            res.addAll(resourService.ListResourByPrjwithLabel(sArray[i]));
        }

        List<String> clss= resourService.findCls();

        return this.ListResourToTree(res,clss);
    }


    @RequestMapping("/ListResourByClsTree")
    @ResponseBody
    public List<ClsVo> ListResour2() {
        List<Resour> res=resourService.ListResour();
        List<String> clss= resourService.findCls();

        return this.ListResourToTree(res,clss);

    }

    @RequestMapping(value = "/ListResourCls", method = RequestMethod.POST)
    @ResponseBody
    public List<String> ListResourCls() {
        List<String> cls = resourService.findCls();
        return cls;
    }

    @RequestMapping(value = "/ListByCls", method = RequestMethod.POST)
    @ResponseBody
    public List<Resour> ListByCls(String cls) {
        List<Resour> resours = resourService.findByCls(cls);
        return resours;
    }

    @RequestMapping("/ListByName")
    @ResponseBody
    public List<Resour> ListResourByName(String rname) {
        return resourService.findByName(rname);
    }

    /**
     * 分页
     * @return
     */
    @RequestMapping(value="/page")
    @ResponseBody
    public List<Resour> page(Integer page){
        int pageNow = page == null ? 1 : page;
        int pageSize = 50;
        int startRows = pageSize*(pageNow-1);
        List<Resour> list = resourService.queryPage(startRows);
        return list;
    }

    /**
     * rows
     * @return
     */
    @RequestMapping(value="/rows")
    @ResponseBody
    public int rows(){
        //System.out.println(resourService.getRowCount());
        return resourService.getRowCount();
    }


    public List<ClsVo> ListResourToTree(List<Resour> res,List<String> clss) {
        ClsVo cc=new ClsVo();
        int numb=-1;
        List<ClsVo> ccc=new ArrayList<ClsVo>();
        cc.setId(numb--);
        cc.setLabel("资源列表");
        cc.setChildren(ccc);

        for(int i=0,size=clss.size();i<size;i++){
            ClsVo cc1=new ClsVo();
            List<ClsVo> ccc1=new ArrayList<ClsVo>();
            cc1.setId(numb--);
            cc1.setLabel(clss.get(i));
            cc1.setChildren(ccc1);

            for(int j=0,size2=res.size();j<size2;j++){
                ClsVo cc2=new ClsVo();
                if(res.get(j).getCls().equals(clss.get(i))){
                    cc2.setId(res.get(j).getId());
                    cc2.setLabel(res.get(j).getRname());
                    cc2.setChildren(null);
                    cc1.getChildren().add(cc2);
                }

            }
            cc.getChildren().add(cc1);
        }

        List<ClsVo> tt=new ArrayList<ClsVo>();
        tt.add(cc);
        return   tt;
    }

    @RequestMapping(value = "/addStar", method = RequestMethod.POST)
    @ResponseBody
    public String addStar(Integer id){
        int result = resourService.addStar(id);
        if (result >= 1) {
            return "点赞成功";
        } else {
            return "点赞失败";
        }
    }

    @RequestMapping(value = "/addClick", method = RequestMethod.POST)
    @ResponseBody
    public int addClick(Integer id) {
        return resourService.addClick(id);
    }
}

