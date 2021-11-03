package com.mzy.hdum.service;

import com.mzy.hdum.model.Resour;

import java.util.List;

public interface ResourService {

    public List<Resour> findByName(String rname);

    public List<Resour> findById(int id);

    public List<Resour> findByCls(String cls);

    public List<Resour> ListResourByPrjwithLabel(String label);

    public List<String> findCls();


    public List<Resour> queryPage(Integer startRows);

    public int getRowCount() ;

    public Resour insertResour(Resour resour) ;

    public List<Resour> ListResour();

    public int Update(Resour resour);

    public int delete(int id);

    public int addStar(Integer id);

    public int addClick(Integer id);
}
