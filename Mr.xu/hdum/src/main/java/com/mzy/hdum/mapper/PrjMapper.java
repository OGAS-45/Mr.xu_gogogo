package com.mzy.hdum.mapper;

import com.mzy.hdum.model.Prj;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrjMapper {

    public List<Prj> findPrjByName(String prjName);

    public List<Prj> findPrjById(int id);

    public List<Prj> ListPrj();

    public List<Prj> queryPage(Integer startRows);

    public int getRowCount();

    public int insertPrj(Prj prj);

    public int delete(int id);

    public int Update(Prj prj);

    public List<Prj> findState();

    public List<Prj> findByState(String state,String prjname);

    public int addStar(Integer id);

    public int addClick(Integer id);
}

