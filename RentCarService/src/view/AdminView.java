package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.AdminController;
import model.dto.ApplyDto;
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
			else if(choose == 2) { addCar(); }
			else if(choose == 3) { findCar(); }
			else if(choose == 4) { updateCar(); }
			else if(choose == 5) { deleteCar(); }
			else if(choose == 6) {
				System.out.println(">> 뒤로가기");
				System.out.println("======== 메인화면 ========");
				break; 
			}
		}
	}
	
	/** 1.신청조회 화면 메소드 */
	public void apply() {
		ArrayList<ApplyDto> result = AdminController.getInstance().apply();
		System.out.println("======== 신청조회 ========");
		System.out.println("순번\t 이름\t전화번호\t\t종류\t보증금\t선납금\t잔존가치\t계약기간");
		for(int index = 0; index < result.size(); index++) {
			ApplyDto ad = result.get(index);
			System.out.printf(" %d\t%s\t%s\t %d\t %d\t %d\t  %d\t  %d\n",
					ad.getAno(), ad.getAname(), ad.getAphone(), ad.getAtype(),
					ad.getDeposit(), ad.getPrepayments(), ad.getResidualValue(), ad.getDuration());
		}
		System.out.println("======== 신청조회 ========");
	}
	
	/** 2. 차량등록 화면 메소드 */
	public void addCar() {
		System.out.println("======== 차량등록 ========");
		System.out.println("======== 카테고리 ========");
		System.out.println("카테고리\t    카테고리\n 번호");
		System.out.println("----------------------");
		select("car");
		System.out.println("----------------------");
		System.out.println("카테고리\t    카테고리\n 번호");
		System.out.println("======== 카테고리 ========");
		System.out.print(">> 국산차 / 수입차 (문자 입력) : "); String cname = scan.next();
		System.out.println("======== 브랜드 ========");
		System.out.println("브랜드        브랜드         카테고리");
		System.out.println(" 번호                       번호");
		System.out.println("-------------------------------");
		select("brand");
		System.out.println("-------------------------------");
		System.out.println("브랜드        브랜드         카테고리");
		System.out.println(" 번호                       번호");
		System.out.println("======== 브랜드 ========");
		System.out.print(">> 브랜드 : "); String bname = scan.next();
		System.out.print(">> 카테고리 번호 : "); int cno = scan.nextInt();
		System.out.println("======== 모델 ========");
		System.out.println(" 모델         모 델         브랜드");
		System.out.println(" 번호                       번호");
		System.out.println("-------------------------------");
		select("model");
		System.out.println("-------------------------------");
		System.out.println(" 모델         모 델         브랜드");
		System.out.println(" 번호                       번호");
		System.out.println("======== 모델 ========");
		System.out.print(">> 모델 : "); String mname = scan.next();
		System.out.print(">> 브랜드 번호 : "); int bno = scan.nextInt();
		System.out.println("======== 등급 ========");
		System.out.println(" 등급          등 급                  가 격\t\t   모델");
		System.out.println(" 번호                                    \t\t   번호");
		System.out.println("---------------------------------------------------------------");
		select("grade");
		System.out.println("---------------------------------------------------------------");
		System.out.println(" 등급          등 급                  가 격\t\t   모델");
		System.out.println(" 번호                                    \t\t   번호");
		System.out.println("======== 등급 ========");
		System.out.print(">> 등급 : "); String gname = scan.next();
		System.out.print(">> 가격 : "); int gprice = scan.nextInt();
		System.out.print(">> 모델 번호 : "); int mno = scan.nextInt();
		Dto dto = new Dto();
		dto.setCno(cno); dto.setBno(bno); dto.setMno(mno);
		dto.setCname(cname); dto.setBname(bname); dto.setMname(mname); dto.setGname(gname); dto.setGprice(gprice);
		boolean result = AdminController.getInstance().addCar(dto);
		if(result) { System.out.println(">> 차량등록 성공"); } else { System.out.println(">> 차량등록 실패"); }
		
	}
	
	/** 2-1. 테이블 출력 메소드 */
	public void select(String tableName) {
		ArrayList<Dto> result = AdminController.getInstance().select(tableName);
		for(int index = 0; index < result.size(); index++) {
			Dto dto = result.get(index);
			if(tableName.equals("car")) {
				System.out.printf("  %-10d %-10s\n", dto.getCno(), dto.getCname());
			} else if(tableName.equals("brand")) {
				System.out.printf("  %-8d   %-8s\t    %d\n", dto.getBno(), dto.getBname(), dto.getCno());
			} else if(tableName.equals("model")) {
				System.out.printf("  %-10d%-6s\t%5d\n", dto.getMno(), dto.getMname(), dto.getBno());
			} else if(tableName.equals("grade")) {
				System.out.printf("  %-10d %-10s    \t%10d\t   %10d\n", dto.getGno(), dto.getGname(), dto.getGprice(), dto.getMno());
			}
		}
	}
	
	/** 3. 차량조회 화면 메소드 */
	public void findCar() {
		ArrayList<Dto> result = AdminController.getInstance().findCar();
		System.out.println("======== 현재 등록된 차량 ========");
		System.out.println("카테고리\t카테고리명\t  브랜드\t브랜드명\t\t모델\t모델명\t\t등급\t    등급명\t         가격");
		System.out.println(" 번호\t\t   번호\t\t\t번호\t\t\t번호");
		System.out.println("------------------------------------------------------------------------------"
				+ "---------------------------");
		for(int index = 0; index < result.size(); index++) {
			Dto dto = result.get(index);
			System.out.printf("  %d\t %s\t    %d    %-7s\t %-5d %-10s\t %-5d\t   %-5s\t%15d\n",
					dto.getCno(), dto.getCname(), dto.getBno(), dto.getBname(), dto.getMno(), dto.getMname(), 
					dto.getGno(), dto.getGname(), dto.getGprice());
		}
		System.out.println("------------------------------------------------------------------------------"
				+ "---------------------------");
		System.out.println("카테고리\t카테고리명\t  브랜드\t브랜드명\t\t모델\t모델명\t\t등급\t    등급명\t         가격");
		System.out.println(" 번호\t\t   번호\t\t\t번호\t\t\t번호");
		System.out.println("======== 현재 등록된 차량 ========");
	}
	
	/** 4. 차량수정 화면 메소드 */
	public void updateCar() {
		System.out.println("======== 차량수정 ========");
		while(true) {			
			System.out.print(">> 1. 브랜드 2.모델 3.등급 4. 뒤로가기 : ");
			int choose = scan.nextInt();
			if(choose == 1) {
				System.out.println("======== 브랜드 ========");
				System.out.println("브랜드        브랜드         카테고리");
				System.out.println(" 번호                       번호");
				System.out.println("-------------------------------");
				select("brand");
				System.out.println("-------------------------------");
				System.out.println("브랜드        브랜드         카테고리");
				System.out.println(" 번호                       번호");
				System.out.println("======== 브랜드 ========");
				System.out.print(">> 브랜드 번호 : "); int bno = scan.nextInt();
				System.out.print(">> 수정 후 브랜드 : "); String bname = scan.next();
				Dto dto = new Dto(); dto.setBno(bno); dto.setBname(bname); dto.setTname("brand");
				boolean result = AdminController.getInstance().updateCar(dto);
				if(result) { System.out.println(">> 브랜드 수정 성공"); } else { System.out.println(">> 브랜드 수정 실패"); }
				
			}
			else if(choose == 2) {
				System.out.println("======== 모델 ========");
				System.out.println(" 모델         모 델         브랜드");
				System.out.println(" 번호                       번호");
				System.out.println("-------------------------------");
				select("model");
				System.out.println("-------------------------------");
				System.out.println(" 모델         모 델         브랜드");
				System.out.println(" 번호                       번호");
				System.out.println("======== 모델 ========");
				System.out.print(">> 모델 번호 : "); int mno = scan.nextInt();
				System.out.print(">> 새로운 모델 : "); String mname = scan.next();
				Dto dto = new Dto(); dto.setMname(mname); dto.setMno(mno); dto.setTname("model");
				boolean result = AdminController.getInstance().updateCar(dto);
				if(result) { System.out.println(">> 모델 수정 성공"); } else { System.out.println(">> 모델 수정 실패"); }
			}
			else if(choose == 3) {
				System.out.println("======== 등급 ========");
				System.out.println(" 등급          등 급                  가 격\t\t   모델");
				System.out.println(" 번호                                    \t\t   번호");
				System.out.println("---------------------------------------------------------------");
				select("grade");
				System.out.println("---------------------------------------------------------------");
				System.out.println(" 등급          등 급                  가 격\t\t   모델");
				System.out.println(" 번호                                    \t\t   번호");
				System.out.println("======== 등급 ========");
				System.out.print(">> 수정할 등급 번호 : "); int gno = scan.nextInt();
				System.out.print(">> 새로운 등급 : "); String gname = scan.next();
				System.out.print(">> 새로운 가격 : "); int gprice = scan.nextInt();
				Dto dto = new Dto();
				dto.setGno(gno); dto.setGname(gname); dto.setGprice(gprice); dto.setTname("grade");
				boolean result = AdminController.getInstance().updateCar(dto);
				if(result) { System.out.println(">> 등급 수정 성공"); } else { System.out.println(">> 등급 수정 실패"); }
			}
			else if(choose == 4) { System.out.println(">> 뒤로가기"); break; }
		}
	}
	
	/** 5. 차량삭제 화면 메소드 */
	public void deleteCar() {
		String result = null;
		System.out.println("======== 차량삭제 ========");
		while(true) {
			System.out.print(">> 1.브랜드 2.모델 3.등급 4.뒤로가기 : ");			
			int choose = scan.nextInt();
			if(choose == 1) {
				System.out.println("======== 브랜드 삭제 ========");
				System.out.println("브랜드        브랜드         카테고리");
				System.out.println(" 번호                       번호");
				System.out.println("-------------------------------");
				select("brand");
				System.out.println("-------------------------------");
				System.out.println("브랜드        브랜드         카테고리");
				System.out.println(" 번호                       번호");
				System.out.println("======== 브랜드 삭제 ========");
				System.out.print(">> 브랜드 번호 : "); int bno = scan.nextInt();
				Dto dto = new Dto(); dto.setBno(bno); dto.setTname("brand");
				result = AdminController.getInstance().deleteCar(dto);
				if(result != null) {
					System.out.println(">> " + result);
					continue;
				} else {
					System.out.println(">> 삭제 실패");
					continue;
				}
			}
			else if(choose == 2) {
				System.out.println("======== 모델 삭제 ========");
				System.out.println(" 모델         모 델         브랜드");
				System.out.println(" 번호                       번호");
				System.out.println("-------------------------------");
				select("model");
				System.out.println("-------------------------------");
				System.out.println(" 모델         모 델         브랜드");
				System.out.println(" 번호                       번호");
				System.out.println("======== 모델 삭제 ========");
				System.out.print(">> 모델 번호 : "); int mno = scan.nextInt();
				Dto dto = new Dto(); dto.setMno(mno); dto.setTname("model");
				result = AdminController.getInstance().deleteCar(dto);
				if(result != null) {
					System.out.println(">> " + result);
					continue;
				} else {
					System.out.println(">> 삭제 실패");
					continue;
				}
			}
			else if(choose == 3) {
				System.out.println("======== 등급 삭제 ========");
				System.out.println(" 등급          등 급                  가 격\t\t   모델");
				System.out.println(" 번호                                    \t\t   번호");
				System.out.println("---------------------------------------------------------------");
				select("grade");
				System.out.println("---------------------------------------------------------------");
				System.out.println(" 등급          등 급                  가 격\t\t   모델");
				System.out.println(" 번호                                    \t\t   번호");
				System.out.println("======== 등급 삭제 ========");
				System.out.print(">> 등급 번호 : "); int gno = scan.nextInt();
				Dto dto = new Dto(); dto.setGno(gno); dto.setTname("grade");
				result = AdminController.getInstance().deleteCar(dto);
				if(result != null) {
					System.out.println(">> " + result);
					continue;
				} else {
					System.out.println(">> 삭제 실패");
					continue;
				}
			}
			else if(choose == 4) { System.out.println(">> 뒤로가기"); break; }
		}
	}
	
}
