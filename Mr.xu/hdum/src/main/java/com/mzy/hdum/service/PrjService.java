package com.mzy.hdum.service;

import org.springframework.stereotype.Service;
import com.mzy.hdum.model.Prj;
import java.util.List;

public interface PrjService {

    public List<Prj> findByName(String prjname);

    public List<Prj> findById(int id);

    public List<Prj> queryPage(Integer startRows);

    public int getRowCount() ;

    public Prj insertPrj(Prj prj) ;

    public List<Prj> ListPrj();
    public int Update(Prj prj);

    public int delete(int id);

    public List<String> findState();

    public List<Prj> findByState(String state, String prjname);

    public int addStar(Integer id);

    public int addClick(Integer id);
}
