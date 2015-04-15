package game.menu;

import java.util.ArrayList;

import game.BombermanGame;
import game.config.GameRoundConfig;
import game.config.InputConfiguration;
import game.config.MapConfig;
import game.config.PlayerConfig;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

public class MainMenu extends Menu{
	
	private GameRoundConfig gameRoundConfig;
	private ArrayList<MapConfig> mapConfigs; 
	private ArrayList<PlayerConfig> playerConfigs;
	private ArrayList<PlayerConfig> actualPlayerConfigs = new ArrayList<PlayerConfig>();
	private ArrayList<InputConfiguration> inputConfigs;

	// MainLayer
	private int mainLayerIndex;
	private int mainLayerSize = 3;
	private String mainLayer_0 = "New Game";
	private String mainLayer_1 = "Controls";
	private String mainLayer_2 = "Exit Game";
	
	// ExitLayer
	private int exitLayerIndex;
	private int exitLayerSize = 2;
	private String exitLayerHeading = "Exit Game ?";
	private String exitLayer_0 = "Yes";
	private String exitLayer_1 = "No";
	
	// ControlsLayer
	private String controlsLayer_0 = "Back";
	
	// SettingsLayer1
	private int settingsLayer1Index;
	private int settingsLayer1Size = 6;
	private int space = 80;
	private int xOffset = 500;
	private int xOffset2 = 550;
	private int yOffset = 630;
	private int timeLimit;
	private int maxTimeLimit = 10;
	private int players;
	private int minPlayers = 2;
	private int maxPlayers = 4;
	private int actualMap;
	private String settingsLayer1_0 = "Start Round!";
	private String settingsLayer1_1 = "Timelimit";
	private String settingsLayer1_2 = "Players";
	private String settingsLayer1_3 = "Avatar Selection";
	private String settingsLayer1_4 = "Map";
	private String settingsLayer1_5 = "Back";
	
	public MainMenu(GameRoundConfig gameRoundConfig, ArrayList<MapConfig> mapConfigs, 
			ArrayList<PlayerConfig> playerConfigs, ArrayList<InputConfiguration> inputConfigs) {
		this.gameRoundConfig 	= gameRoundConfig;
		this.mapConfigs 		= mapConfigs;
		this.playerConfigs 		= playerConfigs;
		this.inputConfigs 		= inputConfigs;
		this.canvasWidth 		= AppGameContainerFSCustom.GAME_CANVAS_WIDTH;
		this.CanvasHeight 		= AppGameContainerFSCustom.GAME_CANVAS_HEIGHT;
	}
	
	public void init() {
		reset();	
		initSettings();
	}
	
	public void reset() {
		resetMainLayer();
		resetExitLayer();
		resetControlsLayer();
		resetSettingsLayer1();
		actualAction = Action.NO_ACTION;
		actualLayer = Layer.MAIN_LAYER;
	}

	private void initSettings() {
		timeLimit = gameRoundConfig.getTimeLimit();
		players = gameRoundConfig.getCurrentPlayerConfigs().size();
		for (MapConfig mc : mapConfigs) {
			if (mc == gameRoundConfig.getMapConfig()) {
				actualMap = mapConfigs.indexOf(mc);
				break;
			}
		}
		for (int i = 0; i < players; i++) {
			actualPlayerConfigs.add(playerConfigs.get(i));
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		switch (actualLayer) {
		
			case MAIN_LAYER:
				renderMainLayer(container, game, graphics);
				break;
			case EXIT_LAYER:
				renderExitLayer(container, game, graphics);
				break;
			case CONTROLS_LAYER:
				renderControlsLayer(container, game, graphics);
				break;
			case SETTINGS_LAYER_1:
				renderSettingsLayer1(container, game, graphics);
				break;
			
			default: break;
		}
	}
	
	private void renderSettingsLayer1(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		int xPaddingMap = 320;
		int xPaddingPlayers = 670;
		int yPadding = 60;
		int playersSpace = 10;
		
		float scale = 0.7f;
		
		mapConfigs.get(actualMap).getImage().draw(xPaddingMap, yPadding + playersSpace / 2, 1.0f);
		fontOutline2.drawString(xPaddingMap - BombermanGame.JAMES_FONT.getWidth("Map") / 2, yPadding - fontOutline2.getHeight("Map") / 2, "Map");
		
		for (int i = 0; i < actualPlayerConfigs.size(); i++) {
			
			PlayerConfig p = actualPlayerConfigs.get(i);
			if (i == 0) {
				p.getImage().draw(xPaddingPlayers, yPadding, 0.5f);
				graphics.scale(scale, scale);
				fontOutline2.drawString((xPaddingPlayers - fontOutline2.getWidth("P1") * scale / 2) / scale , 
						(yPadding - fontOutline2.getHeight("P1") * scale / 2) / scale, "P1");
				graphics.resetTransform();
			}
			if (i == 1) {
				p.getImage().draw(xPaddingPlayers + p.getImage().getScaledCopy(0.5f).getWidth() + playersSpace, yPadding, 0.5f);
				graphics.scale(scale, scale);
				fontOutline2.drawString((xPaddingPlayers + p.getImage().getScaledCopy(0.5f).getWidth() + p.getImage().getScaledCopy(0.5f).getWidth() + playersSpace - fontOutline2.getWidth("P2") * scale / 2) / scale , 
						(yPadding - fontOutline2.getHeight("P2") * scale / 2 ) / scale, "P2");
				graphics.resetTransform();
			}
			if (i == 2) {
				p.getImage().draw(xPaddingPlayers, yPadding + p.getImage().getScaledCopy(0.5f).getHeight() + playersSpace, 0.5f);
				graphics.scale(scale, scale);
				fontOutline2.drawString((xPaddingPlayers - fontOutline2.getWidth("P1") * scale / 2) / scale , 
						(yPadding + p.getImage().getScaledCopy(0.5f).getHeight() + playersSpace - fontOutline2.getHeight("P3") * scale / 2) / scale, "P3");
				graphics.resetTransform();
			}
			if (i == 3) {
				p.getImage().draw(xPaddingPlayers + + p.getImage().getScaledCopy(0.5f).getWidth() + playersSpace, 
						yPadding + p.getImage().getScaledCopy(0.5f).getHeight() + playersSpace, 0.5f);
				graphics.scale(scale, scale);
				fontOutline2.drawString((xPaddingPlayers + p.getImage().getScaledCopy(0.5f).getWidth() + p.getImage().getScaledCopy(0.5f).getWidth() + playersSpace - fontOutline2.getWidth("P4") * scale / 2) / scale , 
						(yPadding + p.getImage().getScaledCopy(0.5f).getHeight() + playersSpace - fontOutline2.getHeight("P4") * scale / 2 ) / scale, "P4");
				graphics.resetTransform();
			}
			
		}
		
		if (settingsLayer1Index == 0) {
			fontOutline.drawString((canvasWidth - fontOutline.getWidth(settingsLayer1_0)) / 2,
					330, settingsLayer1_0);
		}
		else {
			font.drawString((canvasWidth - font.getWidth(settingsLayer1_0)) / 2,
					330, settingsLayer1_0);
		}
		
		graphics.scale(0.7f, 0.7f);
		if (settingsLayer1Index == 1) {
			fontOutline.drawString(xOffset, yOffset, settingsLayer1_1);
			if (timeLimit == 0) {
				fontOutline.drawString(xOffset2 + (canvasWidth - fontOutline.getWidth("<" + "No Limit" + ">")) / 2, 
						yOffset, "<" + "No Limit" + ">");
			}
			else {
				fontOutline.drawString(xOffset2 + (canvasWidth - fontOutline.getWidth("<" + timeLimit + " Min" + ">")) / 2, 
						yOffset, "<" + timeLimit + " Min" + ">");
			}

		}
		else {
			font.drawString(xOffset, yOffset, settingsLayer1_1);
			if (timeLimit == 0) {
				font.drawString(xOffset2 + (canvasWidth - font.getWidth("No Limit")) / 2,
						yOffset, "No Limit");
			}
			else {
				font.drawString(xOffset2 + (canvasWidth - font.getWidth("" + timeLimit + " Min")) / 2,
						yOffset, "" + timeLimit + " Min");
			}
		}
		if (settingsLayer1Index == 2) {
			fontOutline.drawString(xOffset, yOffset + space, settingsLayer1_2);
			fontOutline.drawString(xOffset2 + (canvasWidth - fontOutline.getWidth("<" + players + ">")) / 2,
					yOffset + space, "<" + players + ">");
		}
		else {
			font.drawString(xOffset, yOffset + space, settingsLayer1_2);
			font.drawString(xOffset2 + (canvasWidth - font.getWidth("" + players)) / 2, 
					yOffset + space, "" + players);
		}
		if (settingsLayer1Index == 3) {
			fontOutline.drawString(xOffset, yOffset + 2 * space, settingsLayer1_3);
		}
		else {
			font.drawString(xOffset, yOffset + 2 * space, settingsLayer1_3);
		}
		if (settingsLayer1Index == 4) {
			fontOutline.drawString(xOffset, yOffset + 3 * space, settingsLayer1_4);
			fontOutline.drawString(xOffset2 + (canvasWidth - fontOutline.getWidth("<" + mapConfigs.get(actualMap).getName() + ">")) / 2, 
					yOffset + 3 * space, "<" + mapConfigs.get(actualMap).getName() + ">");
		}
		else {
			font.drawString(xOffset, yOffset + 3 * space, settingsLayer1_4);
			font.drawString(xOffset2 + (canvasWidth - font.getWidth("" + mapConfigs.get(actualMap).getName())) / 2, 
					yOffset + 3 * space, "" + mapConfigs.get(actualMap).getName());
		}
		if (settingsLayer1Index == 5) {
			fontOutline.drawString(xOffset, yOffset + 4 * space, settingsLayer1_5);
		}
		else {
			font.drawString(xOffset, yOffset + 4 * space, settingsLayer1_5);
		}
		
		graphics.resetTransform();
	}

	private void renderControlsLayer(GameContainer container, StateBasedGame game, Graphics graphics) {
		fontOutline2.drawString(100, 50, "Controls");
		
		graphics.scale(0.7f, 0.7f);
		fontOutline.drawString((canvasWidth - fontOutline.getWidth(controlsLayer_0)) / 2 /0.7f, 
				650 /0.7f, controlsLayer_0);
		graphics.resetTransform();
	}

	private void renderExitLayer(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		font.drawString((canvasWidth - font.getWidth(exitLayerHeading)) / 2, 450, exitLayerHeading);
		
		float scale = 0.7f;
		graphics.scale(scale, scale);
		
		if (exitLayerIndex == 0) {
			fontOutline.drawString((canvasWidth / 2 - fontOutline.getWidth(exitLayer_0) - 15) /scale, 570 /scale, exitLayer_0);
		}
		else {
			font.drawString((canvasWidth / 2 - font.getWidth(exitLayer_0) - 15) /scale, 570 /scale, exitLayer_0);
		}
		if (exitLayerIndex == 1) {
			fontOutline.drawString((canvasWidth / 2 + fontOutline.getWidth(exitLayer_1) - 15) /scale, 570 /scale, exitLayer_1);
		}
		else {
			font.drawString((canvasWidth / 2 + font.getWidth(exitLayer_1) - 15) /scale, 570 /scale, exitLayer_1);
		}
		
		graphics.resetTransform();
	}

	private void renderMainLayer(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		if (mainLayerIndex == 0) {
			fontOutline.drawString((canvasWidth - fontOutline.getWidth(mainLayer_0)) / 2, 380, mainLayer_0);
		}
		else {
			font.drawString((canvasWidth - font.getWidth(mainLayer_0)) / 2, 380, mainLayer_0);
		}
		if (mainLayerIndex == 1) {
			fontOutline.drawString((canvasWidth - fontOutline.getWidth(mainLayer_1)) / 2, 480, mainLayer_1);
		}
		else {
			font.drawString((canvasWidth - font.getWidth(mainLayer_1)) / 2, 480, mainLayer_1);
		}
		if (mainLayerIndex == 2) {
			fontOutline.drawString((canvasWidth - fontOutline.getWidth(mainLayer_2)) / 2, 580, mainLayer_2);
		}
		else {
			font.drawString((canvasWidth - font.getWidth(mainLayer_2)) / 2, 580, mainLayer_2);
		}
		
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		checkInput(container.getInput());
	}
	
	private void checkInput(Input input) {
		
		switch (actualLayer) {
		
			case MAIN_LAYER:
				mainLayer_checkInput(input);
				break;
			case EXIT_LAYER:
				exitLayer_checkInput(input);
				break;
			case CONTROLS_LAYER:
				controlsLayer_checkInput(input);
				break;
			case SETTINGS_LAYER_1:
				settingsLayer1_checkInput(input);
				break;
			
			default: break;
		}
	}

	private void settingsLayer1_checkInput(Input input) {
		
		if (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {
			if (settingsLayer1Index - 1 >= 0) {
				settingsLayer1Index--;
			}
		}
		if (input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)) {
			if (settingsLayer1Index + 1 < settingsLayer1Size) {
				settingsLayer1Index++;
			}
		}
		if (input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)) {
			switch (settingsLayer1Index) {
				case 1: 
					if (timeLimit - 1 >= 0) {
						timeLimit--;
					}
					break;
				case 2: 
					if (players - 1 >= minPlayers) {
						players--;
						actualPlayerConfigs.remove(players);
					}
					break;
				case 4: 
					if (actualMap - 1 >= 0) {
						actualMap--;
					}
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)) {
			switch (settingsLayer1Index) {
				case 1: 
					if (timeLimit + 1 <= maxTimeLimit) {
						timeLimit++;
					}
					break;
				case 2: 
					if (players + 1 <= maxPlayers) {
						players++;
						actualPlayerConfigs.add(playerConfigs.get(0));
					}
					break;
				case 4: 
					if (actualMap + 1 < mapConfigs.size()) {
						actualMap++;
					}
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch (settingsLayer1Index) {
			
				case 0: 
					createGameRoundConfig();
					actualAction = Action.START_GAME_ROUND;
					break;
				case 3: 
					break;
				case 5: 
					actualLayer = Layer.MAIN_LAYER;
					resetSettingsLayer1();
					break;
			
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualLayer = Layer.MAIN_LAYER;
			resetSettingsLayer1();
		}
		
	}

	private void controlsLayer_checkInput(Input input) {
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			actualLayer = Layer.MAIN_LAYER;
			resetControlsLayer();
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualLayer = Layer.MAIN_LAYER;
			resetControlsLayer();
		}
		
	}

	private void exitLayer_checkInput(Input input) {
		
		if (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {}
		if (input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)) {}
		if (input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)) {
			if (exitLayerIndex - 1 >= 0) {
				exitLayerIndex--;
			}
		}
		if (input.isKeyPressed(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)) {
			if (exitLayerIndex + 1 < exitLayerSize) {
				exitLayerIndex++;
			}
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch (exitLayerIndex) {
				
				case 0: 
					actualAction = Action.EXIT_GAME;
					break;
				case 1: 
					actualLayer = Layer.MAIN_LAYER;
					resetExitLayer();
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualLayer = Layer.MAIN_LAYER;
			resetExitLayer();
		}
		
	}

	private void mainLayer_checkInput(Input input) {
		
		if (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {
			if (mainLayerIndex - 1 >= 0) {
				mainLayerIndex--;
			}
		}
		if (input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)) {
			if (mainLayerIndex + 1 < mainLayerSize) {
				mainLayerIndex++;
			}
		}
		if (input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)) {}
		if (input.isKeyPressed(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)) {}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch (mainLayerIndex) {
				
				case 0: 
					actualLayer = Layer.SETTINGS_LAYER_1;
					break;
				case 1: 
					actualLayer = Layer.CONTROLS_LAYER;
					break;
				case 2: 
					actualLayer = Layer.EXIT_LAYER;
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualLayer = Layer.EXIT_LAYER;
		}
	}
	
	private void resetSettingsLayer1() {
		settingsLayer1Index = 0;
	}

	private void resetControlsLayer() {
		
	}

	private void resetExitLayer() {
		exitLayerIndex = 0;
	}

	private void resetMainLayer() {
		mainLayerIndex = 0;	
	}
	
	private void createGameRoundConfig() {
		gameRoundConfig.setCurrentPlayerConfigs(actualPlayerConfigs);
		gameRoundConfig.setMapConfig(mapConfigs.get(actualMap));
		gameRoundConfig.setTimeLimit(timeLimit);
		
		ArrayList<InputConfiguration> input = new ArrayList<InputConfiguration>();
		for (int i = 0; i < players; i++) {
			if (i < inputConfigs.size()) {
				input.add(inputConfigs.get(i));
			}
		}
	}

	public Action getActualAction() {
		return actualAction;
	}

	public void setActualAction(Action actualAction) {
		this.actualAction = actualAction;
	}

	public GameRoundConfig getGameRoundConfig() {
		return gameRoundConfig;
	}

	public void setGameRoundConfig(GameRoundConfig gameRoundConfig) {
		this.gameRoundConfig = gameRoundConfig;
	}
}
