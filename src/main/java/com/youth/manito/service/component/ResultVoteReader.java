package com.youth.manito.service.component;

import com.youth.manito.domain.entity.Manito;
import com.youth.manito.domain.entity.ResultVote;
import com.youth.manito.domain.repository.ResultVoteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResultVoteReader {

    private final ResultVoteRepository resultVoteRepository;

    public List<ResultVote> getAllByManitos(List<Manito> manitos) {
        List<Long> manitoIds = manitos.stream().map(Manito::getId).toList();
        return resultVoteRepository.findAllByManitoIdIn(manitoIds);
    }
}
