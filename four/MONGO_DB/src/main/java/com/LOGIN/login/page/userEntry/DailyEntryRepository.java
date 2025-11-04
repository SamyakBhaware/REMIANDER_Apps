package com.LOGIN.login.page.userEntry;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DailyEntryRepository extends CrudRepository<DailyEntry, Long>, PagingAndSortingRepository<DailyEntry, Long> {
    DailyEntry findByIdAndOwner(Long id, String owner);
    Page<DailyEntry> findAllByOwner(String owner, PageRequest pageRequest);

    boolean existsByIdAndOwner(Long id, String owner);
}
