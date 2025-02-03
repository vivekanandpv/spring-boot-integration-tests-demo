package in.athenaeum.springbootintegrationtestsdemo.repositories;

import in.athenaeum.springbootintegrationtestsdemo.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
