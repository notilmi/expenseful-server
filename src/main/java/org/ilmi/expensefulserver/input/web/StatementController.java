package org.ilmi.expensefulserver.input.web;

import jakarta.validation.Valid;
import org.ilmi.expensefulserver.domain.StatementType;
import org.ilmi.expensefulserver.input.web.data.input.CreateStatementDTO;
import org.ilmi.expensefulserver.input.web.data.input.UpdateStatementDTO;
import org.ilmi.expensefulserver.input.web.data.output.StatementDTO;
import org.ilmi.expensefulserver.input.web.data.output.mapper.StatementDTOMapper;
import org.ilmi.expensefulserver.security.ExpensefulUserDetails;
import org.ilmi.expensefulserver.service.StatementService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statement")
public class StatementController {
    private final StatementService statementService;
    private final StatementDTOMapper statementDTOMapper;

    public StatementController(StatementService statementService, StatementDTOMapper statementDTOMapper) {
        this.statementService = statementService;
        this.statementDTOMapper = statementDTOMapper;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public StatementDTO createStatement(
            @RequestBody @Valid CreateStatementDTO request,
            @AuthenticationPrincipal ExpensefulUserDetails userDetails
    ) {
        var createdStatement = statementService.createStatement(
                request.getTitle(),
                request.getCategory(),
                request.getAmount(),
                request.getDate(), // Convert to yyyy-MM-dd
                StatementType.valueOf(request.getType()),
                userDetails.getId()
        );

        return statementDTOMapper.toDTO(createdStatement);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public StatementDTO updateStatement(
            @AuthenticationPrincipal ExpensefulUserDetails userDetails,
            @PathVariable String id,
            @RequestBody @Valid UpdateStatementDTO request
            ) {

        var updatedStatement = statementService.updateStatement(
                id,
                request.getTitle(),
                request.getCategory(),
                request.getAmount(),
                request.getDate(),
                userDetails.getId()
        );

        return statementDTOMapper.toDTO(updatedStatement);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteStatement(
            @PathVariable String id,
            @AuthenticationPrincipal ExpensefulUserDetails userDetails
    ) {
        statementService.deleteStatement(id, userDetails.getId());
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<StatementDTO> getStatements(
            @AuthenticationPrincipal ExpensefulUserDetails userDetails
    ) {
        var statementList = statementService.getAllStatements(userDetails.getId());

        return statementList.stream()
                .map(statementDTOMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public StatementDTO getStatement(
            @PathVariable String id,
            @AuthenticationPrincipal ExpensefulUserDetails userDetails
    ) {
        var statement = statementService.getStatementById(id, userDetails.getId());

        return statementDTOMapper.toDTO(statement);
    }
}
