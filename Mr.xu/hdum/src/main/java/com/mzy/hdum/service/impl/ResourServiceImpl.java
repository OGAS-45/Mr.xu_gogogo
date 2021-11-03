package com.mzy.hdum.service.impl;

import com.mzy.hdum.mapper.ResourMapper;
import com.mzy.hdum.model.Resour;
import com.mzy.hdum.service.ResourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourServiceImpl implements ResourService {

    @Autowired
    private ResourMapper resourMapper;

    @Override
    public List<Resour> findByName(String rname) {
        return resourMapper.findResourByName(rname);
    }

    @Override
    public List<Resour> findById(int id) {
        return resourMapper.findResourById(id);
    }


    @Override
    public List<Resour> findByCls(String cls) {
        return resourMapper.findResourByCls(cls);
    }
    @Override
    public List<String> findCls() {
        List<String> cc=new ArrayList<String>();
        List<Resour> rs=resourMapper.findCls();
        for(int i=0;i<rs.size();i++)
        {
            cc.add(rs.get(i).getCls());
        }
        return cc;
    }
    @Override
    public List<Resour> queryPage(Integer startRows) {
        return resourMapper.queryPage(startRows);
    }
    @Override
    public int getRowCount() {
        return resourMapper.getRowCount();
    }
    @Override
    public Resour insertResour(Resour resour) {
        resourMapper.insertResour(resour);
        return resour;
    }
    @Override
    public List<Resour> ListResour(){
        return resourMapper.ListResour();
    }

    public List<Resour> ListResourByPrjwithLabel(String label){
        return resourMapper.ListResourByPrjwithLabel(label);
    }
    @Override
    public int Update(Resour Resour){
        return resourMapper.Update(Resour);
    }
    @Override
    public int delete(int id){
        return resourMapper.delete(id);
    }

    @Override
    public int addStar(Integer id) {
        return resourMapper.addStar(id);
    }

    @Override
    public int addClick(Integer id) {
        return resourMapper.addClick(id);
    }
}