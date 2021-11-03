package com.mzy.hdum.service.impl;

import com.mzy.hdum.mapper.PrjMapper;
import com.mzy.hdum.model.Prj;
import com.mzy.hdum.model.Resour;
import com.mzy.hdum.service.PrjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrjServiceImpl implements PrjService {

    @Autowired
    private PrjMapper prjMapper;

    @Override
    public List<Prj> findByName(String prjName) {
        return prjMapper.findPrjByName(prjName);
    }

    @Override
    public List<Prj> findById(int id) {
        return prjMapper.findPrjById(id);
    }

    @Override
    public List<Prj> queryPage(Integer startRows) {
        return prjMapper.queryPage(startRows);
    }
    @Override
    public int getRowCount() {
        return prjMapper.getRowCount();
    }
    @Override
    public Prj insertPrj(Prj prj) {
        prjMapper.insertPrj(prj);
        return prj;
    }
    @Override
    public List<Prj> ListPrj(){
        return prjMapper.ListPrj();
    }
    @Override
    public int Update(Prj prj){
        return prjMapper.Update(prj);
    }
    @Override
    public int delete(int id){
        return prjMapper.delete(id);
    }

    @Override
    public List<String> findState() {
        List<String> cc=new ArrayList<String>();
        List<Prj> rs = prjMapper.findState();
        for(int i=0;i<rs.size();i++)
        {
            cc.add(rs.get(i).getState());
        }
        return cc;
    }

    @Override
    public List<Prj> findByState(String state, String prjname) {
        if(state != null)
            return prjMapper.findByState(state,prjname);
        else
            return prjMapper.findPrjByName(prjname);
    }

    @Override
    public int addStar(Integer id) {
        return prjMapper.addStar(id);
    }

    @Override
    public int addClick(Integer id) {
        return prjMapper.addClick(id);
    }
}