package web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import entity.User;
import service.NameException;
import service.PasswordException;
import service.UserService;
import util.JsonResult;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	
	
	
	@RequestMapping(value="/download.do",produces="application/vnd.ms-excel")
	@ResponseBody
	public byte[] download(HttpServletResponse res) throws IOException{
		res.addHeader("Content-Disposition", "attachment; filename=\"hello.xls\"");
		//����Excel����������
		HSSFWorkbook workbook=new HSSFWorkbook();
		//�ڹ������д���������
		HSSFSheet sheet=workbook.createSheet();
		//����һ��
		HSSFRow row=sheet.createRow(0);
		//c����һ������
		HSSFCell cell=row.createCell(0);
		//������������
		cell.setCellValue("Hello World");
		//��Excel�������л�Ϊbyte[]����
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		workbook.write(out);
		workbook.close();
		byte[] buf=out.toByteArray();
		return buf;
	}
	
	// UserController
		@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
		@ResponseBody
		public String upload(MultipartFile photo, String name) throws IOException {

			// MultipartFile ��Spring�ṩ��API
			// ��װ���������ݵ���Ϣ��ԭʼ�ļ�����
			// �ļ������ͣ�mime ContentType��
			// �ļ������ݣ�byte[] ���� ������
			// ����ʱ�ؼ� name ���Ե�ֵ

			// ��ȡԭʼ�ļ���
			String filename = photo.getOriginalFilename();
			// ��ȡ�ļ�����
			String type = photo.getContentType();
			// �ļ�������
			byte[] data = photo.getBytes();

			File file = new File("d:/", filename);
			FileOutputStream out = new FileOutputStream(file);
			out.write(data);
			out.close();

			return name + "Success!";
		}
	
		@RequestMapping(value = "/img2.do", produces = "image/png")
		@ResponseBody
		public byte[] img2(HttpServletResponse res) throws IOException {

			// ���� Content-Disposition ͷ������ʵ��
			// �����ļ����ܣ���ο� RFC2616 19.5.1�½�
			// http://doc.tedu.cn/rfc/rfc2616.txt

			res.addHeader("Content-Disposition", "attachment; filename=\"girl.png\"");
			BufferedImage img = new BufferedImage(100, 50, BufferedImage.TYPE_3BYTE_BGR);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(img, "png", out);
			byte[] png = out.toByteArray();
			return png;
		}
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping("/login.do")
	// ��½����
	public Object login(String name, String pwd, HttpServletResponse res) {
		Cookie cookie = new Cookie("token", "123");
		cookie.setPath("/");
		User u = userService.login(name, pwd);
		// ���û����е�token�·����������
		cookie.setValue(u.getToken());
		res.addCookie(cookie);
		// Map<String,Object> json=new HashMap<String,Object>();
		// json.put("state", "0");
		// json.put("data", u);
		// return json;
		return new JsonResult(u);

	}

	// produces="image/png" �������� ContentType ͷ
	@RequestMapping(value = "/img.do", produces = "image/png")
	@ResponseBody
	public byte[] img() throws IOException {
		// @ResponseBody �Զ�������ֵ
		// �����Java Bean ����ΪJSON
		// ���byte[] �ͽ�byte����䵽����
		// ������Ϣ�� body�С�
		// new ����Ƭ����ȥ
		BufferedImage img = new BufferedImage(200, 56, BufferedImage.TYPE_3BYTE_BGR);
		// �� img ���б��� Ϊ png ��ʽ
		// FileOutputStream out = new ...;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		// �õ�����
		byte[] png = out.toByteArray();
		return png;
	}

	

	// ע�ᴦ��
	@ResponseBody
	@RequestMapping("/regis.do")
	public JsonResult regist(String name, String pwd, String confirm, String nick) {
		User user = userService.regist(name, nick, pwd, confirm);
		return new JsonResult(user);
	}

	// ���������쳣
	@ResponseBody
	@ExceptionHandler(NameException.class)
	public Object nameExc(NameException e) {
		e.printStackTrace();
		return new JsonResult(2, e);
	}

	// �����������
	@ResponseBody
	@ExceptionHandler(PasswordException.class)
	public Object passExc(PasswordException e) {
		e.printStackTrace();
		return new JsonResult(3, e);
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public JsonResult exc(Exception e) {
		e.printStackTrace();
		return new JsonResult(4, e);
	}
}
