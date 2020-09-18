package com.sist.dao;
import java.io.*;
import java.util.*;
/*
 *   Collection 클래스  ==> 자료 구조 (데이터 저장하는 방법)
 *   ========== 데이터를 저장하고 쉽게 저장이 가능하게 만든 클래스의 집합 
 *     List ==============> 오라클
 *      = 순서를 가지고 있다 
 *      = 데이터가 중복이 가능하다 
 *      = ***ArrayList,Vector,LinkedList
 *     Set  =============> 사용빈도가 거의 없다 
 *      = 순서가 없다 
 *      = 데이터가 중복이 없다 
 *      = HashSet , TreeSet  
 *     Map
 *      = 두개를 동시에 저장이 가능 (키(id),값) => request,response
 *        ?no=10  ==> request.push("no",10)
 *      = ***HashMap(hashtable의 단점을 보완),hashtable
 *      = JSON(MongoDB)
 *      = (id,"admin")
 *      = 클래스 등록 , sql등록 
 *      =================================== 저장 방법 
 */
/*
 *   1. XML을 읽어와서 => 데이터 저장 
 *                    ========= 셋팅 (getConnection(),disConnection())
 *       SqlSessionFactory(Mybatis에서 지원하는 클래스)
 *       =================
 *       XML 파싱 
 *   2. id를 설정해주고 sql을 실행이 가능하게 만든다 
 */

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
public class RecipeDAO {
   private static SqlSessionFactory ssf;
   // 자동 처리 => 변수에 초기화
   static 
   {
	   try
	   {
		   // 1. XML 파일 읽기 
		   Reader reader=Resources.getResourceAsReader("Config.xml");
		   // 파일명이 대소문자 구분 
		   // 파싱 (XML에 등록된 데이터 읽기) build(xml 파일설정)
		   // 파싱한 결과를 ssf에 첨부 => getConnection(),disConnection()
		   ssf=new SqlSessionFactoryBuilder().build(reader); // SAX를 이용해서 등록된 데이터 
		   // Map에 저장을 놓고 사용자가 요청시마다 처리 => id => SQL문장을 실행한 결과를 넘겨준다 
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();// 예외처리를 하지 않으면 에러 처리가 어렵다 (XML안에서 에러)
	   }
   }
   // 활용 
   /*
    *   start , end => Map
    */
   public static List<RecipeVO> recipeListData(Map map)
   {
	   List<RecipeVO> list=new ArrayList<RecipeVO>();
	   SqlSession session=null;
	   // Connection
	   // 에러 처리 => 예외처리
	   try
	   {
		   session=ssf.openSession();// Connection이 미리 만들어져 있다 (만들어진 Connection객체 주소를 가지고 온다)
		   list=session.selectList("recipeListData",map);
		   //                       id , #{}에 값을 채운다
		   /*
		    *   selectList(String id)
		    *   selectList(String id,Object obj)
		    *   Object => 모든 데이터형을 받을 수 있는 클래스 
		    *   int,String ,Map ,~VO
		    *   받을 수 있는 데이터가 1개 ==> 여러개를 전송 => Map으로 묶어서 전송,~VO로 묶어서 전송 
		    */
	   }catch(Exception ex)
	   {
		   // 에러 처리
		   System.out.println(ex.getMessage());
	   }
	   finally
	   {
		   if(session!=null)
			   session.close();
	   }
	   return list;
   }
}








