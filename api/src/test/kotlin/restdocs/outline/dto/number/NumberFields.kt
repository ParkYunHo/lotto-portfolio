package restdocs.outline.dto.number

import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.request.QueryParametersSnippet
import org.springframework.restdocs.request.RequestDocumentation

/**
 * @author yoonho
 * @since 2023.08.03
 */
object NumberFields {

    fun queryParameters(): QueryParametersSnippet =
        RequestDocumentation.queryParameters(
            RequestDocumentation.parameterWithName("drwtNo").description("로또 회차")
        )

    fun responseFields(): ResponseFieldsSnippet =
        PayloadDocumentation.responseFields(
            fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
            fieldWithPath("status").type(JsonFieldType.STRING).description("응답상태"),
            fieldWithPath("data.drwtNo").type(JsonFieldType.NUMBER).description("로또 회차"),
            fieldWithPath("data.drwtDate").type(JsonFieldType.STRING).description("로또 당첨일자"),
            fieldWithPath("data.drwtNo1").type(JsonFieldType.NUMBER).description("로또 당첨번호1"),
            fieldWithPath("data.drwtNo2").type(JsonFieldType.NUMBER).description("로또 당첨번호2"),
            fieldWithPath("data.drwtNo3").type(JsonFieldType.NUMBER).description("로또 당첨번호3"),
            fieldWithPath("data.drwtNo4").type(JsonFieldType.NUMBER).description("로또 당첨번호4"),
            fieldWithPath("data.drwtNo5").type(JsonFieldType.NUMBER).description("로또 당첨번호5"),
            fieldWithPath("data.drwtNo6").type(JsonFieldType.NUMBER).description("로또 당첨번호6"),
            fieldWithPath("data.bnusNo").type(JsonFieldType.NUMBER).description("로또 보너스번호"),
            fieldWithPath("data.totSellamnt").type(JsonFieldType.NUMBER).description("총 판매금액"),
            fieldWithPath("data.firstWinamnt").type(JsonFieldType.NUMBER).description("1등 1게임당 당첨금액"),
            fieldWithPath("data.firstPrzwnerCo").type(JsonFieldType.NUMBER).description("1등 당첨게임수"),
            fieldWithPath("data.firstAccumamnt").type(JsonFieldType.NUMBER).description("1등 전체 당첨금액")
        )
}