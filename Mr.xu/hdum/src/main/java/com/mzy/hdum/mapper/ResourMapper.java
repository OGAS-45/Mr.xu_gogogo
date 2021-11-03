package com.mzy.hdum.mapper;

import com.mzy.hdum.model.Resour;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourMapper {

    public List<Resour> findResourByName(String resourName);

    public List<Resour> findResourById(int id);

    public List<Resour> findResourByCls(String cls);

    public List<Resour> ListResourByPrjwithLabel(String label);

    public List<Resour> findCls();

    public List<Resour> ListResour();

    public List<Resour> queryPage(Integer startRows);

    public int getRowCount();

    public int insertResour(Resour prj);

    public int delete(int id);

    public int Update(Resour prj);

    public int addStar(Integer id);

    public int addClick(Integer id);
}

