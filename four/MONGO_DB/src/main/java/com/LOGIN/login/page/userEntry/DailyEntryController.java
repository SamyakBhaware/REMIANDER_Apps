package com.LOGIN.login.page.userEntry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/entries")
public class DailyEntryController {

    private final DailyEntryRepository dailyEntryRepository;

    DailyEntryController(DailyEntryRepository dailyEntryRepository) {
        this.dailyEntryRepository = dailyEntryRepository;
    }

    private DailyEntry findDailyEntryById(Long id,  Principal principal) {
        return dailyEntryRepository.findByIdAndOwner(id, principal.getName());
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<DailyEntry> findById(@PathVariable Long requestedId, Principal principal) {
        DailyEntry dailyEntry = findDailyEntryById(requestedId, principal);

        if (dailyEntry != null) {
            return ResponseEntity.ok(dailyEntry);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping       //<List<DailyEntry>> => <Page<DailyEntry>>
    private ResponseEntity<List<DailyEntry>> findAll(Pageable pageable, Principal principal) {
        Page<DailyEntry> page = dailyEntryRepository.findAllByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "dailyText"))
                )
        );
        return ResponseEntity.ok(page.getContent());

    }


    @PostMapping
    private ResponseEntity<Void> createDailyEntry(@RequestBody DailyEntry dailyEntry,
                                                  UriComponentsBuilder ucb, Principal principal) {

        DailyEntry dailyEntryWithOwner =
                new DailyEntry(null,
                        dailyEntry.getDailyText(),
                        dailyEntry.getFixedText(),
                        principal.getName());

        DailyEntry savedDailyEntry = dailyEntryRepository.save(dailyEntryWithOwner);

        URI locationOfNewDailyEntry = ucb
                .path("/entries/{requestedId}")
                .buildAndExpand(savedDailyEntry.getId())
                .toUri();;
        return ResponseEntity.created(locationOfNewDailyEntry).build();
    }



    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putDailyEntry(@PathVariable Long requestedId,
                                               @RequestBody DailyEntry dailyEntryUpdate, Principal principal){

//        DailyEntry dailyEntry = dailyEntryRepository.findByIdAndOwner(requestedId, principal.getName());

        DailyEntry dailyEntry = findDailyEntryById(requestedId, principal);

        if(dailyEntry != null) {
            DailyEntry updatedDailyEntry =
                    new DailyEntry(dailyEntry.getId(),
                            dailyEntryUpdate.getDailyText(),
                            dailyEntryUpdate.getFixedText(),
                            principal.getName());

            dailyEntryRepository.save(updatedDailyEntry);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteDailyEntry(@PathVariable Long id, Principal principal) {

        if(!dailyEntryRepository.existsByIdAndOwner(id, principal.getName())){
            return ResponseEntity.notFound().build();
        }
        dailyEntryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
