package org.tiger.graphqlbeta.endpoint;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.tiger.graphqlbeta.api.Auther;
import org.tiger.graphqlbeta.api.Book;
import org.tiger.graphqlbeta.db.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class Query implements GraphQLQueryResolver {

    private final Repository repo = new Repository();

    public List<ApiBook> allBooks() {
        return repo.getBooks().stream().map(ApiBook::new).collect(Collectors.toList());
    }

    public ApiBook bookById(String id) {
        return new ApiBook(repo.getBook(id));
    }


    /** Using API  POJO to wrap domain object. */
    private class ApiBook {
        private Book original;
        ApiBook(Book original) { this.original = original; }
        public String getId() {
            return original.getId();
        }
        public String getName() {
            return original.getName();
        }
        public int pagesCount() { return original.getPages(); }
        public Auther getAuthor() {
            return original.getAuthor();
        }
    }
}
