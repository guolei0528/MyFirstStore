package  com.project.backMng.admin.voidCard.ParkVoidCardMng.service;

import java.util.List;

import com.project.backMng.admin.voidCard.ParkVoidCardMng.model.ParkVoidCardBean;
import com.project.backMng.platuser.sys.AreaInfoMng.model.AreaInfoBean;
import com.project.backMng.platuser.sys.ParkInfoMng.model.ParkInfoBean;
import com.redoak.jar.base.model.ObjectMap;
import com.redoak.jar.base.model.Page;
import com.redoak.jar.base.model.SysLoginUserBean;

public interface ParkVoidCardMngService {
	
	public List<ParkVoidCardBean> findList(ObjectMap queryParam,Page page);
	
	public ParkVoidCardBean findInfo(String mv_license,String plate_color);
	
	public void save(ParkVoidCardBean bean,SysLoginUserBean userBean);
	
	public void update(ParkVoidCardBean bean,SysLoginUserBean userBean);
	
	public void delete(ObjectMap map,SysLoginUserBean userBean);
	
	public List<ParkInfoBean> findParkList();
	
	public List<AreaInfoBean> findAreaList(String park_id);
	
	public void synchronizeList();
}