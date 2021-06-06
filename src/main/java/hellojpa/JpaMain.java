package hellojpa;

import org.hibernate.event.spi.SaveOrUpdateEvent;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // db랑 연결된다.

        EntityManager entityManager = emf.createEntityManager(); // 고객이 요청이 올때마다 생성된다.
        //code
        // JPA 모든 데이터 변경은 트랜잭션 안에서 이루어져야 한다.
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
//            // 객체를 생선한 상태(비영속)
            Member member = new Member(240L, "A");
            Member member2 = new Member(250L, "B");
            Member member3 = new Member(260L, "C");


            entityManager.persist(member);
            entityManager.persist(member2);
            entityManager.persist(member3);

            List<Member> members = entityManager.createQuery("select m from Member m", Member.class).getResultList();
//
            System.out.println("=============");
//            for (Member member1 : members) {
//                System.out.println(member1.getName());
//            }

            transaction.commit();


//            // 객체를 저장한 상태(영속)
//           entityManager.persist(member);
//
//           // 회원 엔티티를 영속성 컨텍스트에서 분리(준영속 상태)
//           entityManager.detach(member);
//
//           // 객체를 삭제한 상태(삭제)
//           entityManager.remove(member);


//            Member member = entityManager.find(Member.class, 1L);
//            member.setName("HelloJPA");
            // JPQL
            // 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
            // 특정 데이터베이스 SQL에 의존X
//            List<Member> resultList = entityManager.createQuery("select m from Member as m", Member.class) // 객체를 대상으로 하는 객체 지향 쿼리
//                    .setFirstResult(5)
//                    .setMaxResults(8)
//                    .getResultList();
//
//            for (Member member : resultList) {
//                System.out.println("memberName =  " + member.getName());
//            }

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close(); // database connection을 물고 동작하기 때문에 꼭 닫아줘야한다.
        }
        emf.close();

    }
}
