package controller;
import java.util.ArrayList;
import model.dao.RentDao;
import model.dto.RentDto;

public class RentController {
   // 싱글톤 패턴
   private static RentController instance = new RentController();
   private RentController() {}
   public static RentController getInstance() { return instance; }
   
   // 카테고리(국산/수입) 목록 조회
   public ArrayList<RentDto> getCategoryList() {
       return RentDao.getInstance().getCagegoryList();  // 메소드명 오타 수정 (Cagegory -> Category)
   }
   
   // 브랜드 목록 조회
   public ArrayList<RentDto> getBrandList(int cno) {
       return RentDao.getInstance().getBrandList(cno);
   }
   
   // 모델 목록 조회  
   public ArrayList<RentDto> getModelList(int bno) {
       return RentDao.getInstance().getModelList(bno);
   }
   
   // 등급 목록 조회
   public ArrayList<RentDto> getGradeList(int mno) {
       return RentDao.getInstance().getGradeList(mno);
   }
   
   // 등급 상세 정보
   public RentDto getGradeInfo(int gno) {
       return RentDao.getInstance().getGradeInfo(gno);
   }
   
   // 견적 계산
   public int calculateMonthlyPayment(int carPrice, int deposit, int prepayment, int duration) {
       int totalAmount = carPrice - (carPrice * deposit / 100) - (carPrice * prepayment / 100);
       return totalAmount / duration;
   }
   
   // 신청 등록 (registerApplication1 메소드 제거하고 하나로 통일)
   public boolean registerApplication(RentDto dto) {
       // 입력값 검증 후 등록
       if(validateInput(dto)) {
           return RentDao.getInstance().registerApplication(dto);
       }
       return false;
   }
   
   // 입력값 검증 (수정된 조건에 맞게)
   public boolean validateInput(RentDto dto) {
       // 전화번호 형식 검증
       if(!dto.getAphone().matches("\\d{3}-\\d{4}-\\d{4}")) {
           return false;
       }
       
       // 계약기간 검증 (36, 48, 60개월만 가능)
       if(dto.getDuration() != 36 && dto.getDuration() != 48 && dto.getDuration() != 60) {
           return false;
       }
       
       // 보증금, 선납금 범위 검증 (0, 30, 50%만 가능)
       if(dto.getDeposit() != 0 && dto.getDeposit() != 30 && dto.getDeposit() != 50) {
           return false;
       }
       if(dto.getPrepayments() != 0 && dto.getPrepayments() != 30 && dto.getPrepayments() != 50) {
           return false;
       }
       
       // 잔존가치 범위 검증 (30, 40, 50%만 가능)
       if(dto.getResidualValue() != 30 && dto.getResidualValue() != 40 && dto.getResidualValue() != 50) {
           return false;
       }
       
       return true;
   }
}