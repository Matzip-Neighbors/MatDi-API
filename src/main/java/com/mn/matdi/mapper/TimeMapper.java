package com.mn.matdi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TimeMapper {

    @Select("select NOW()")
    public String getTime();

    public String getTime2();
}
