package com.trade_analysis.controller;

import com.trade_analysis.dtos.StockQueryDto;
import com.trade_analysis.dtos_validation.StockQueryValidationResult;
import com.trade_analysis.dtos_validation.StockQueryValidator;
import com.trade_analysis.exception.UserNotFoundException;
import com.trade_analysis.model.StockPricesPeriod;
import com.trade_analysis.model.StockSymbol;
import com.trade_analysis.model.User;
import com.trade_analysis.service.StockMarketService;
import com.trade_analysis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.trade_analysis.dtos_validation.StockQueryValidationResult.SUCCESS;
import static java.util.Arrays.asList;

@Controller
public class StockPriceController {
    @Autowired
    StockMarketService stockMarketService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/stocks")
    @PreAuthorize(value = "isAuthenticated()")
    public String getStockPrices(Model model) throws UserNotFoundException {
        User user = userService.getUserByUsername(getUsername());
        String apiKey = user.getApiKey() == null ? "" : user.getApiKey();

        StockQueryDto stockQueryDto = new StockQueryDto(apiKey);
        model.addAttribute("stockQueryDto", stockQueryDto);
        model.addAttribute("periods", asList(StockPricesPeriod.values()));
        model.addAttribute("symbols", asList(StockSymbol.values()));

        return "stock-prices-preview";
    }

    @PostMapping(value = "/stocks")
    @PreAuthorize(value = "isAuthenticated()")
    public String stockPrices(@ModelAttribute StockQueryDto stockQueryDto, Model model) {
        model.addAttribute("stockQueryDto", stockQueryDto);
        model.addAttribute("periods", asList(StockPricesPeriod.values()));
        model.addAttribute("symbols", asList(StockSymbol.values()));

        StockQueryValidationResult validationResult = StockQueryValidator.fullValidator.validate(stockQueryDto);

        if(validationResult != SUCCESS) {
            model.addAttribute("info", validationResult);
        }
        else {
            model.addAttribute("result", stockMarketService.getFromStockQueryDto(stockQueryDto));
        }
        return "stock-prices-preview";
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
