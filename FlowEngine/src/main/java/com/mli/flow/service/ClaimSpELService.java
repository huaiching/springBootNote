package com.mli.flow.service;

import com.mli.flow.entity.FlowDefinitionEntity;
import com.mli.flow.repository.ClaimHistoryRepository;
import com.mli.flow.repository.ClaimStatusRepository;
import com.mli.flow.repository.FlowDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.*;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 理賠流程引擎 SpEL 相關方法
 */
@Service
public class ClaimSpELService {
    private static final Logger log = LoggerFactory.getLogger(ClaimSpELService.class);

    @Autowired
    private FlowDefinitionRepository flowDefinitionRepository;
    @Autowired
    private ClaimStatusRepository claimStatusRepository;
    @Autowired
    private ClaimHistoryRepository claimHistoryRepository;

    /**
     * SpEL 表達式解析器全局單例: 避免 多次 new 浪費記憶體
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    /**
     * 表達式緩存
     * - Key: ruleCode 字串
     * - Value: 已解析的 Expression 對象
     */
    private final Map<String, Expression> expressionCache = new ConcurrentHashMap<>();


    /**
     * 理賠 流程引擎 - 取得 下一個節點
     * @param dataMap SpEL 變數
     * @return
     */
    public String getNextStatus(Map<String, Object> dataMap) {
        // 取得 流程定義表
        List<FlowDefinitionEntity> FlowDefinitionEntityList = getFlowDefinitionEntity();

        // 規則檢核
        EvaluationContext context = new StandardEvaluationContext();
        dataMap.forEach(context::setVariable);

        // spEL 規則解析
        Boolean result = Boolean.FALSE;
        for (FlowDefinitionEntity flowDefinitionEntity : FlowDefinitionEntityList) {
            String ruleCode = flowDefinitionEntity.getSpelExpression();
            try {
                // 從緩存獲取或解析（Key 是完整的 ruleCode）
                Expression expression = expressionCache.computeIfAbsent(ruleCode, code -> {
                    // 檢查緩存大小，防止無限增長
                    if (expressionCache.size() >= 1000) {
                        log.warn("表達式緩存已達上限 {}，清除最舊的 20% 項目", 1000);
                        evictOldestEntries(expressionCache);
                    }
                    return PARSER.parseExpression(code);
                });

                result = expression.getValue(context, Boolean.class);
            } catch (ParseException e) {
                // 語法錯誤（規則寫錯）
                log.error("【getNextStatus - SpEL 語法錯誤】檢核規則={} 錯誤訊息={}",
                        ruleCode, e.getMessage());
            } catch (EvaluationException e) {
                // 執行期錯誤（變數 null、類型不符等）
                log.error("【getNextStatus - SpEL 執行失敗】檢核規則={} 錯誤訊息={}",
                        ruleCode, e.getMessage());
            } catch (Exception e) {
                // 其他未知錯誤
                log.error("【getNextStatus - SpEL 未知異常】檢核規則={}",
                        ruleCode, e);
            }

            if (Boolean.TRUE.equals(result)) {
                return flowDefinitionEntity.getNextStatus();
            }
        }

        return null;
    }

    /**
     * 理賠 流程引擎 - 取得 上一個節點
     * @param dataMap SpEL 變數
     * @return
     */
    public String getPrewStatus(Map<String, Object> dataMap) {
        // 取得 流程定義表
        List<FlowDefinitionEntity> FlowDefinitionEntityList = getFlowDefinitionEntity();

        // 規則檢核
        EvaluationContext context = new StandardEvaluationContext();
        dataMap.forEach(context::setVariable);

        // spEL 規則解析
        Boolean result = Boolean.FALSE;
        for (FlowDefinitionEntity flowDefinitionEntity : FlowDefinitionEntityList) {
            String ruleCode = flowDefinitionEntity.getSpelExpression();
            try {
                // 從緩存獲取或解析（Key 是完整的 ruleCode）
                Expression expression = expressionCache.computeIfAbsent(ruleCode, code -> {
                    // 檢查緩存大小，防止無限增長
                    if (expressionCache.size() >= 1000) {
                        log.warn("表達式緩存已達上限 {}，清除最舊的 20% 項目", 1000);
                        evictOldestEntries(expressionCache);
                    }
                    return PARSER.parseExpression(code);
                });

                result = expression.getValue(context, Boolean.class);
            } catch (ParseException e) {
                // 語法錯誤（規則寫錯）
                log.error("【getPrewStatus - SpEL 語法錯誤】檢核規則={} 錯誤訊息={}",
                        ruleCode, e.getMessage());
            } catch (EvaluationException e) {
                // 執行期錯誤（變數 null、類型不符等）
                log.error("【getPrewStatus - SpEL 執行失敗】檢核規則={} 錯誤訊息={}",
                        ruleCode, e.getMessage());
            } catch (Exception e) {
                // 其他未知錯誤
                log.error("【getPrewStatus - SpEL 未知異常】檢核規則={}",
                        ruleCode, e);
            }

            if (Boolean.TRUE.equals(result)) {
                return flowDefinitionEntity.getPrewStatus();
            }
        }

        return null;
    }

    /**
     * 取得 流程定義表
     */
    private List<FlowDefinitionEntity> getFlowDefinitionEntity() {
        return flowDefinitionRepository.findAll();
    }

    /**
     * 當緩存滿時，清除最舊的 20% 項目
     */
    private void evictOldestEntries(Map<String, Expression> expressionCache) {
        int removeCount = 1000 / 5; // 清除 20%
        Iterator<String> iterator = expressionCache.keySet().iterator();

        int removed = 0;
        while (iterator.hasNext() && removed < removeCount) {
            iterator.next();
            iterator.remove();
            removed++;
        }

        log.info("已清除 {} 個舊的表達式緩存項目", removed);
    }
}
