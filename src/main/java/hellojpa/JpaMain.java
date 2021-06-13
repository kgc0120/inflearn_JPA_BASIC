package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
            //저장
            Team team = new Team();
            team.setName("TeamA");
            entityManager.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            Member member1 = entityManager.find(Member.class, member.getId());

            List<Member> members = member1.getTeam().getMembers();
            for (Member member2 : members) {
                System.out.println(member2.getTeam().getName());
            }


            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close(); // database connection을 물고 동작하기 때문에 꼭 닫아줘야한다.
        }
        emf.close();

    }
}
