package com.hardcore.utility.gui;

import com.hardcore.utility.HardcoreUtility;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends Screen {
    private String selectedCategory = "Combat";
    private final List<ButtonWidget> categoryButtons = new ArrayList<>();
    private final List<ButtonWidget> moduleButtons = new ArrayList<>();

    public ConfigScreen() {
        super(new LiteralText("Hardcore Utility - Navigator"));
    }

    @Override
    protected void init() {
        refreshButtons();
    }

    private void refreshButtons() {
        this.buttons.clear();
        this.children.clear();
        categoryButtons.clear();
        moduleButtons.clear();

        int sidebarWidth = 80;
        int xOffset = 10;
        int yOffset = 40;

        // Add Category Buttons
        addCategoryButton(xOffset, yOffset, "Combat");
        addCategoryButton(xOffset, yOffset + 25, "Movement");
        addCategoryButton(xOffset, yOffset + 50, "Visual");
        addCategoryButton(xOffset, yOffset + 75, "Player");

        // Add Module Buttons based on selected category
        int moduleX = xOffset + sidebarWidth + 20;
        int moduleY = 40;

        switch (selectedCategory) {
            case "Combat":
                addModuleButton(moduleX, moduleY, "KillAura", () -> HardcoreUtility.killAura = !HardcoreUtility.killAura, HardcoreUtility.killAura);
                addModuleButton(moduleX, moduleY + 25, "AutoTotem", () -> HardcoreUtility.autoTotem = !HardcoreUtility.autoTotem, HardcoreUtility.autoTotem);
                break;
            case "Movement":
                addModuleButton(moduleX, moduleY, "Flight", () -> HardcoreUtility.flight = !HardcoreUtility.flight, HardcoreUtility.flight);
                addModuleButton(moduleX, moduleY + 25, "Speed", () -> HardcoreUtility.speed = !HardcoreUtility.speed, HardcoreUtility.speed);
                addModuleButton(moduleX, moduleY + 50, "Spider", () -> HardcoreUtility.spider = !HardcoreUtility.spider, HardcoreUtility.spider);
                addModuleButton(moduleX, moduleY + 75, "Jesus", () -> HardcoreUtility.jesus = !HardcoreUtility.jesus, HardcoreUtility.jesus);
                addModuleButton(moduleX, moduleY + 100, "Step", () -> HardcoreUtility.step = !HardcoreUtility.step, HardcoreUtility.step);
                break;
            case "Visual":
                addModuleButton(moduleX, moduleY, "FullBright", () -> HardcoreUtility.fullBright = !HardcoreUtility.fullBright, HardcoreUtility.fullBright);
                addModuleButton(moduleX, moduleY + 25, "X-Ray", () -> {
                    HardcoreUtility.xray = !HardcoreUtility.xray;
                    if (client != null && client.worldRenderer != null) client.worldRenderer.reload();
                }, HardcoreUtility.xray);
                break;
            case "Player":
                addModuleButton(moduleX, moduleY, "God Mode", () -> HardcoreUtility.godMode = !HardcoreUtility.godMode, HardcoreUtility.godMode);
                addModuleButton(moduleX, moduleY + 25, "FastPlace", () -> HardcoreUtility.fastPlace = !HardcoreUtility.fastPlace, HardcoreUtility.fastPlace);
                addModuleButton(moduleX, moduleY + 50, "NoFall", () -> HardcoreUtility.noFall = !HardcoreUtility.noFall, HardcoreUtility.noFall);
                break;
        }
    }

    private void addCategoryButton(int x, int y, String name) {
        String label = (selectedCategory.equals(name) ? "§6> " : "") + name;
        ButtonWidget btn = new ButtonWidget(x, y, 70, 20, new LiteralText(label), button -> {
            selectedCategory = name;
            refreshButtons();
        });
        this.addButton(btn);
        categoryButtons.add(btn);
    }

    private void addModuleButton(int x, int y, String name, Runnable action, boolean state) {
        String label = name + ": " + (state ? "§aON" : "§cOFF");
        ButtonWidget btn = new ButtonWidget(x, y, 120, 20, new LiteralText(label), button -> {
            action.run();
            button.setMessage(new LiteralText(name + ": " + (getStatus(name) ? "§aON" : "§cOFF")));
        });
        this.addButton(btn);
        moduleButtons.add(btn);
    }

    private boolean getStatus(String name) {
        switch (name) {
            case "God Mode": return HardcoreUtility.godMode;
            case "Flight": return HardcoreUtility.flight;
            case "KillAura": return HardcoreUtility.killAura;
            case "FullBright": return HardcoreUtility.fullBright;
            case "Speed": return HardcoreUtility.speed;
            case "AutoTotem": return HardcoreUtility.autoTotem;
            case "Spider": return HardcoreUtility.spider;
            case "FastPlace": return HardcoreUtility.fastPlace;
            case "NoFall": return HardcoreUtility.noFall;
            case "Jesus": return HardcoreUtility.jesus;
            case "Step": return HardcoreUtility.step;
            case "X-Ray": return HardcoreUtility.xray;
            default: return false;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        
        // Draw Sidebar Background
        fill(matrices, 5, 35, 85, this.height - 5, 0x88000000);
        
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
