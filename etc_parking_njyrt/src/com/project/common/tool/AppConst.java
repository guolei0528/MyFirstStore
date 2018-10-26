package com.project.common.tool;
/**
*枚举值定义工具
*生成日期：2017-05-04 11:44:31
*/
public class AppConst{
	/**
	*布尔变量
	*/
	public interface BOOLEAN{
		public final static String COL_TYPE="BOOLEAN";
		/**
		*是
		*/
		public final static int YES=1;
		/**
		*否
		*/
		public final static int NO=2;
	}
	/**
	*用户类型
	*/
	public interface USER_TYPE{
		public final static String COL_TYPE="USER_TYPE";
		/**
		*系统管理员
		*/
		public final static int SYS_ADMIN=1;
		/**
		*平台用户
		*/
		public final static int PLAT_USER=2;
	}
	/**
	*删除标志位
	*/
	public interface DELETE_FLAG{
		public final static String COL_TYPE="DELETE_FLAG";
		/**
		*是
		*/
		public final static int YES=1;
		/**
		*否
		*/
		public final static int NO=0;
	}
	/**
	*启用的标志位
	*/
	public interface IN_USE_FLAG{
		public final static String COL_TYPE="IN_USE_FLAG";
		/**
		*启用
		*/
		public final static int YES=1;
		/**
		*未启用
		*/
		public final static int NO=0;
	}
	/**
	*操作类型
	*/
	public interface OPE_TYPE{
		public final static String COL_TYPE="OPE_TYPE";
		/**
		*新增
		*/
		public final static int ADD=1;
		/**
		*修改
		*/
		public final static int EDIT=2;
		/**
		*删除
		*/
		public final static int DELETE=3;
		/**
		*其他
		*/
		public final static int OTHER=4;
	}
	/**
	*性别
	*/
	public interface SEX{
		public final static String COL_TYPE="SEX";
		/**
		*男
		*/
		public final static int MALE=1;
		/**
		*女
		*/
		public final static int FEMALE=0;
	}
	
	public interface VALID_TYPE{
		String TEMP_USER="00";
		String POLICE_VOID="01";
		String PARK_VOID="02";
		String WHITE_LIST_USER="03";
		String EXPIRE_MEMBER="04";
		String EXISTED="05";
	}
}