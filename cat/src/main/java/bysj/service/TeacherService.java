package bysj.service;

import bysj.dao.TeacherDao;
import bysj.domain.Teacher;

import java.sql.SQLException;
import java.util.Collection;

public final class TeacherService {
	private static TeacherDao teacherDao= TeacherDao.getInstance();
	private static TeacherService teacherService=new TeacherService();
	private TeacherService(){}
	
	public static TeacherService getInstance(){
		return teacherService;
	}
	
	public Collection<Teacher> findAll()throws SQLException {
		return teacherDao.findAll();
	}
	
	public Teacher find(Integer id)throws SQLException{
		return teacherDao.find(id);
	}
	
	public boolean update(Teacher teacher) throws SQLException,ClassNotFoundException {
		return teacherDao.update(teacher);
	}

	public void add(Teacher teacher)throws SQLException{
		teacherDao.add(teacher);
	}

	public void delete(Integer id) throws SQLException,ClassNotFoundException{
		teacherDao.delete(id);
	}
	
//	public boolean add(Teacher teacher)throws SQLException{
//		return teacherDao.add(teacher);
//	}
//
//	public boolean delete(Integer id) throws SQLException,ClassNotFoundException{
//		Teacher teacher = this.find(id);
//		return teacherDao.delete(teacher);
//	}
	
	public boolean delete(Teacher teacher)throws SQLException,ClassNotFoundException{
		//如果该教师有关联的课题，不能删除
		if(teacher.getProjects().size()>0){
			return false;
		}else {
			teacherDao.delete(teacher.getId());
			return true;
		}
	}
}
