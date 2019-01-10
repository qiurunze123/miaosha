package com.geekq.miaosha.dao;

import com.geekq.miaosha.domain.MiaoShaMessage;
import com.geekq.miaosha.domain.MiaoShaMessageUser;
import com.geekq.miaosha.domain.MiaoshaGoods;
import com.geekq.miaosha.domain.MiaoshaUser;
import com.geekq.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MiaoShaMessageDao {
	
	@Select("select * from miaosha_message where messageid =  #{messageid}  ")
	public List<MiaoShaMessage> listMiaoShaMessage(@Param("messageId") String messageId);
	@Select("<script>select * from miaosha_message_user where 1=1 <if test=\"messageId !=null \">and messageId = #{messageId} </if></script>")
	public List<MiaoShaMessageUser> listMiaoShaMessageUser(@Param("messageId") String messageId);
//	@Insert("insert into miaosha_user (id , nickname ,password , salt ,head,register_date,last_login_date)value (#{id},#{nickname},#{password},#{salt},#{head},#{registerDate},#{lastLoginDate}) ")
//	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
//	public void insertMiaoShaUser(MiaoshaUser miaoshaUser);
}
