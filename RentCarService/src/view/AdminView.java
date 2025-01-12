package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.AdminController;
import model.dto.AdminDto;
import model.dto.Dto;

public class AdminView {
	// singleton start
	private static AdminView instance = new AdminView();
	private AdminView() {}
	public static AdminView getInstance() { return instance; }
	// singleton end
	
	Scanner scan = new Scanner(System.in);
	
	/** 관리자모드 접속 화면 */
	public void pattern() {
		String pwd = scan.next();
		boolean result = AdminController.getInstance().pattern(pwd);
		if(result) { run(); }
		else { }
	}
	
	/** 관리자모드 메인 화면 */
	public void run() {
		System.out.println("======== 관리자모드 ========");
		while(true) {
			System.out.print("1.신청조회 2.차량등록 3.차량조회 4.차량수정 5.차량삭제 6.뒤로가기 : ");
			int choose = scan.nextInt();
			if(choose == 1) { apply(); }
			else if(choose == 2) { add2(); }
			else if(choose == 3) {}
			else if(choose == 4) {}
			else if(choose == 5) {}
			else if(choose == 6) {
				System.out.println(">> 뒤로가기");
				System.out.println("======== 메인화면 ========");
				break; 
			}
		}
	}
	
	/** 1.신청조회 화면 메소드 */
	public void apply() {
		ArrayList<AdminDto> result = AdminController.getInstance().apply();
		System.out.println("======== 신청조회 ========");
		System.out.println(">> 순번\t이름\t전화번호\t\t종류\t보증금\t선납금\t잔존가치\t계약기간");
		for(int index = 0; index < result.size(); index++) {
			AdminDto ad = result.get(index);
			System.out.printf(">> %d\t%s\t%s\t%d\t%d\t%d\t%d\t%d\n",
					ad.getAno(), ad.getAname(), ad.getAphone(), ad.getAtype(),
					ad.getDeposit(), ad.getPrepayments(), ad.getResidualValue(), ad.getDuration());
		}
		System.out.println("======== 신청조회 ========");
	}
	
	/** 2. 차량등록 화면 메소드 */
	public void addCar() {
		boolean result = false;
		System.out.println("======== 차량등록 ========");
		while(true) {			
			System.out.print(">> 1. 국산차/수입차 2.브랜드 3.모델 4.등급,가격 5.뒤로가기 : ");
			int choose = scan.nextInt();
			if(choose == 1) {
				System.out.println("======== 카테고리 테이블 ========");
				select("car");
				System.out.println("======== 카테고리 테이블 ========");
				System.out.print(">> 국산차 / 수입차 : "); String cname = scan.next();
				Dto dto = new Dto();
				dto.setCname(cname);
				result = AdminController.getInstance().addCar("car", "cname", dto);
			}
			else if(choose == 2) {
				System.out.println("======== 브랜드 테이블 ========");
				select("brand");
				System.out.println("======== 브랜드 테이블 ========");
				System.out.print(">> 브랜드 : "); String bname = scan.next();
				System.out.print(">> 번호 : "); int cno = scan.nextInt();
				Dto dto = new Dto();
				dto.setBname(bname); dto.setCno(cno);
				result = AdminController.getInstance().addCar("brand", "bname", dto);
			}
			else if(choose == 3) {
				System.out.println("======== 모델 테이블 ========");
				select("model");
				System.out.println("======== 모델 테이블 ========");
				System.out.print(">> 모델 : "); String mname = scan.next();
				System.out.print(">> 번호 : "); int bno = scan.nextInt();
				Dto dto = new Dto();
				dto.setMname(mname);
				result = AdminController.getInstance().addCar("model", "mname", dto);
			}
			else if(choose == 4) {
				System.out.println("======== 등급 테이블 ========");
				select("grade");
				System.out.println("======== 등급 테이블 ========");
				System.out.print(">> 등급 : "); String gname = scan.next();
				System.out.print(">> 가격 : "); int gprice = scan.nextInt();
				System.out.print(">> 번호 : "); int mno = scan.nextInt();
				Dto dto = new Dto();
				dto.setGname(gname);
				dto.setGprice(gprice);
				result = AdminController.getInstance().addCar("grade", "gname", dto);
			}
			else if(choose == 5) { System.out.println("======== 관리자모드 ========"); break; }
			if(result) {
				System.out.println(">> 등록 성공");
			} else {
				System.out.println(">> 등록 실패");
				System.out.println("======== 차량등록 ========");
			}
		}
	}
	
	public void add() {
		System.out.println("======== 차량등록 ========");
		findCar();
		System.out.print(">> 국산차 / 수입차 : "); String cname = scan.next();
		System.out.print(">> 카테고리번호 : "); int cno = scan.nextInt();
		System.out.print(">> 브랜드 : "); String bname = scan.next();
		System.out.print(">> 브랜드 번호 : "); int bno = scan.nextInt();
		System.out.print(">> 모델 : "); String mname = scan.next();
		System.out.print(">> 모델 번호 : "); int mno = scan.nextInt();
		System.out.print(">> 등급명 : "); String gname = scan.next();
		System.out.print(">> 가격 : "); int gprice = scan.nextInt();
		Dto dto = new Dto();
		dto.setCname(cname); dto.setBname(bname); dto.setMname(mname); dto.setGname(gname); dto.setGprice(gprice);
		dto.setCno(cno); dto.setBno(bno); dto.setMno(mno);
		System.out.println("등록 : " + dto.toString());
		AdminController.getInstance().add(dto);
	}
	
	public void add2() {
		System.out.println("======== 차량등록 ========");
		System.out.println("======== 카테고리 ========");
		select2("car");
		System.out.println("======== 카테고리 ========");
		System.out.print(">> 국산차 / 수입차 : "); String cname = scan.next();
		System.out.println("======== 브랜드 ========");
		select2("brand");
		System.out.println("======== 브랜드 ========");
		System.out.print(">> 브랜드 : "); String bname = scan.next();
		System.out.print(">> 카테고리 번호 : "); int cno = scan.nextInt();
		System.out.println("======== 모델 ========");
		select2("model");
		System.out.println("======== 모델 ========");
		System.out.print(">> 모델 : "); String mname = scan.next();
		System.out.print(">> 브랜드 번호 : "); int bno = scan.nextInt();
		System.out.println("======== 등급 ========");
		select2("grade");
		System.out.println("======== 등급 ========");
		System.out.print(">> 등급 : "); String gname = scan.next();
		System.out.print(">> 가격 : "); int gprice = scan.nextInt();
		System.out.print(">> 모델 번호 : "); int mno = scan.nextInt();
		Dto dto = new Dto();
		dto.setCno(cno); dto.setBno(bno); dto.setMno(mno);
		dto.setCname(cname); dto.setBname(bname); dto.setMname(mname); dto.setGname(gname); dto.setGprice(gprice);
		boolean result = AdminController.getInstance().add2(dto);
		if(result) { System.out.println(">> 차량등록 성공"); } else { System.out.println(">> 차량등록 실패"); }
		
	}
	
	/** 2-1. 테이블 출력 메소드 */
	public void select2(String tableName) {
		ArrayList<Dto> result = AdminController.getInstance().select2(tableName);
		for(int index = 0; index < result.size(); index++) {
			Dto dto = result.get(index);
			if(tableName.equals("car")) {
				System.out.printf("%d\t%s\n", dto.getCno(), dto.getCname());
			} else if(tableName.equals("brand")) {
				System.out.printf("%d\t%s\t%d\n", dto.getBno(), dto.getBname(), dto.getCno());
			} else if(tableName.equals("model")) {
				System.out.printf("%d\t%s\t%d\n", dto.getMno(), dto.getMname(), dto.getBno());
			} else if(tableName.equals("grade")) {
				System.out.printf("%d\t%s\t%d\t%d\n", dto.getGno(), dto.getGname(), dto.getGprice(), dto.getMno());
			}
		}
	}
	
	
	public void select(String tableName) {
		ArrayList<Dto> result = AdminController.getInstance().select(tableName);
		if(tableName.equals("car")) {}
		else if(tableName.equals("brand")) {}
		else if(tableName.equals("model")) { System.out.println("  모델명\t     브랜드 번호"); }
		else if(tableName.equals("grade")) {}
		for(int index = 0; index < result.size(); index++) {
			Dto dto = result.get(index);
			if(tableName.equals("car")) {
				System.out.printf("%d\t%s\n", dto.getCno(), dto.getCname());
			} else if(tableName.equals("brand")) {
				System.out.printf("%d\t%s\t%d\n", dto.getBno(), dto.getBname(), dto.getCno());
			} else if(tableName.equals("model")) {
				System.out.printf("  %-10s\t%d\n",dto.getMname(), dto.getBno());
			} else if(tableName.equals("grade")) {
				System.out.printf("%d\t%s\t%d\t%d\n", dto.getMno(), dto.getMname(), dto.getGprice(), dto.getBno());
			}
		}
	}
	
	/** 3. 차량조회 화면 메소드 */
	public void findCar() {
		ArrayList<Dto> result = AdminController.getInstance().findCar();
		System.out.println("======== 현재 등록된 차량 ========");
		System.out.println("카테고리\t카테고리명\t  브랜드\t브랜드명\t모델\t모델명\t\t등급\t 등급명\t\t      가격");
		System.out.println(" 번호\t\t   번호\t\t번호\t\t\t번호");
		for(int index = 0; index < result.size(); index++) {
			Dto dto = result.get(index);
			System.out.printf("  %d\t  %s\t    %d\t %s\t %d\t%-10s\t %d\t%-10s\t%12d\n",
					dto.getCno(), dto.getCname(), dto.getBno(), dto.getBname(), dto.getMno(), dto.getMname(), 
					dto.getGno(), dto.getGname(), dto.getGprice());
		}
		System.out.println("\n카테고리\t카테고리명\t  브랜드\t브랜드명\t모델\t모델명\t\t등급\t 등급명\t\t      가격");
		System.out.println(" 번호\t\t   번호\t\t번호\t\t\t번호");
		System.out.println("======== 현재 등록된 차량 ========");
	}
	
	/** 4. 차량수정 화면 메소드 */
	public void updateCar() {
		
	}
	
	/** 5. 차량삭제 화면 메소드 */
	public void deleteCar() {
		
	}
	
}
