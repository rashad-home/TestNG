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
public class RadioButton extends Element {

    public RadioButton(WebDriver driver, By elementIdentifier) {
        super(driver, elementIdentifier);
    }

    public boolean isSelected() {
        return getWebElement().isSelected();
    }

    public void select() {
        if (!getWebElement().isSelected()) {
            getWebElement().click();
        }

    }

    public void unSelect() {
        if (getWebElement().isSelected()) {
            getWebElement().click();
        }
    }
}
