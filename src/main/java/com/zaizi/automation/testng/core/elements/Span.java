/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zaizi.automation.testng.core.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author mketheeswaran
 */
public class Span extends Element {

    public Span(WebDriver driver, By elementIdentifier) {
        super(driver, elementIdentifier);
        
    }
    
    public String getText(){
        return getWebElement().getText();
    }
}
