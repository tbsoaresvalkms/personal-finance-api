package com.coelhocaique.finance.api.handler

import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.dto.ObjectMapper.toIncomeDTO
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.RANGE_DATE
import com.coelhocaique.finance.api.handler.FetchCriteria.SearchType.REFERENCE_DATE
import com.coelhocaique.finance.api.handler.RequestParameterHandler.extractBody
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveAccountId
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrieveParameters
import com.coelhocaique.finance.api.handler.RequestParameterHandler.retrievePath
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForIncome
import com.coelhocaique.finance.api.helper.LinkBuilder.buildForIncomes
import com.coelhocaique.finance.api.helper.Messages.NO_PARAMETERS
import com.coelhocaique.finance.api.helper.RequestValidator.validate
import com.coelhocaique.finance.api.helper.ResponseHandler.generateResponse
import com.coelhocaique.finance.api.helper.exception.ApiException.ApiExceptionHelper.business
import com.coelhocaique.finance.core.domain.dto.IncomeResponse
import com.coelhocaique.finance.core.service.IncomeService
import com.coelhocaique.finance.core.util.logger
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.error
import reactor.core.publisher.Mono.just

@Component
class IncomeHandler (private val service: IncomeService) {

    fun create(req: ServerRequest): Mono<ServerResponse> {
        return retrieveAccountId(req)
                .flatMap { extractBody<IncomeRequestDTO>(req).map { itt -> itt.copy(accountId = it) } }
                .flatMap { validate(it) }
                .flatMap { service.create(toIncomeDTO(it)) }
                .flatMap { just(buildForIncome(req.uri().toString(), it)) }
                .let { generateResponse(it, 201) }
    }

    fun findById(req: ServerRequest): Mono<ServerResponse> {
        return retrievePath(req)
                .flatMap { service.findById(it.accountId, it.id!!) }
                .flatMap { just(buildForIncome(req.uri().toString(), it)) }
                .let { generateResponse(it) }
    }

    fun fetchIncomes(req: ServerRequest): Mono<ServerResponse> {
        return retrieveParameters(req)
                .flatMap {
                    logger().info(it.toString())
                    when (it.searchType()) {
                        REFERENCE_DATE -> findByReferenceDate(it)
                        RANGE_DATE -> findByReferenceDateRange(it)
                        else -> error(business(NO_PARAMETERS))
                    }
                }
                .flatMap { buildForIncomes(req.uri().toString(), it) }
                .let { generateResponse(it, onEmptyStatus = 204) }
    }

    fun deleteById(req: ServerRequest): Mono<ServerResponse> {
        return retrievePath(req)
                .flatMap { service.deleteById(it.accountId, it.id!!) }
                .let { generateResponse(it, 204) }
    }

    private fun findByReferenceDate(it: FetchCriteria): Mono<List<IncomeResponse>> =
            service.findByReferenceDate(it.accountId, it.referenceDate!!)


    private fun findByReferenceDateRange(it: FetchCriteria): Mono<List<IncomeResponse>> =
            service.findByReferenceDateRange(it.accountId, it.dateFrom!!, it.dateTo!!)
}