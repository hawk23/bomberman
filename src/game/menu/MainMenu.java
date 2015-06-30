package game.menu;

import java.util.ArrayList;

import game.BombermanGame;
import game.config.GameRoundConfig;
import game.config.GameSettings;
import game.config.InputConfiguration;
import game.config.MapConfig;
import game.config.PlayerConfig;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import slick.extension.AppGameContainerFSCustom;

public class MainMenu extends Menu
{
	
	private static final String errorSoundPath		= "res/sounds/menu/error.wav";
	private Sound errorSound;
	private static final String successSoundPath		= "res/sounds/menu/success.wav";
	private Sound successSound;
	
	private GameRoundConfig 				gameRoundConfig 	= new GameRoundConfig();
	private ArrayList<MapConfig> 			mapConfigs 			= new ArrayList<MapConfig>();
	private ArrayList<PlayerConfig> 		playerConfigs 		= new ArrayList<PlayerConfig>();
	private ArrayList<PlayerConfig> 		actualPlayerConfigs = new ArrayList<PlayerConfig>();
	private ArrayList<InputConfiguration> 	inputConfigs 		= new ArrayList<InputConfiguration>(); 
	private ArrayList<InputConfiguration>	actualInputConfigs	= new ArrayList<InputConfiguration>();
	
	private Image avatar_back;
	private Image key_wasd;
	private Image key_arrow;
	private Image pad_1;
	private Image pad_2;
	private Image map_controls;
	private static final String key_wasd_path 			= "res/controls/key_wasd.png";
	private static final String key_arrow_path 			= "res/controls/key_arrow.png";
	private static final String pad_1_path 				= "res/controls/pad_1.png";
	private static final String pad_2_path 				= "res/controls/pad_2.png";
	private static final String map_controls_path 		= "res/controls/map_controls.png";
	private static final String avatarBackgroundPath 	= "res/visuals/backgrounds/avatar_background.png";

	// MainLayer
	private int mainLayerIndex;
	private int mainLayerSize 	= 3;
	private String mainLayer_0 	= "New Game";
	private String mainLayer_1 	= "Controls";
	private String mainLayer_2 	= "Exit Game";
	
	// ExitLayer
	private int exitLayerIndex;
	private int exitLayerSize 		= 2;
	private String exitLayerHeading = "Exit Game ?";
	private String exitLayer_0 		= "Yes";
	private String exitLayer_1		= "No";
	
	// ControlsLayer
	private int controlsLayerSize;
	private int controlsLayerIndex = 0;
	private String controlsLayer_0 = "Back";
	private String controlsLayer_1 = "Save";
	private String controlsLayer_2 = "Player 1";
	private String controlsLayer_3 = "Player 2";
	private String controlsLayer_4 = "Player 3";
	private String controlsLayer_5 = "Player 4";
	private boolean p1_error;
	private boolean p2_error;
	private boolean p3_error;
	private boolean p4_error;
	private int player1_index		= -1;
	private int player2_index		= -1;
	private int player3_index		= -1;
	private int player4_index		= -1;
	private int p1_sic, p2_sic, p3_sic, p4_sic = -1;
	
	// SettingsLayer1
	private int settingsLayer1Index;
	private int settingsLayer1Size 		= 6;
	private int settings_space 			= 80;
	private int settings_xOffset 		= 500;
	private int settings_xOffset2 		= 550;
	private int settings_yOffset 		= 690;
	private int timeLimit;
	private int maxTimeLimit 			= 10;
	private int players;
	private int minPlayers 				= 2;
	private int maxPlayers;
	private int actualMap;
	private String settingsLayer1_0 	= "Start Round!";
	private String settingsLayer1_1 	= "Timelimit";
	private String settingsLayer1_2 	= "Players";
	private String settingsLayer1_3 	= "Avatar Selection";
	private String settingsLayer1_4	 	= "Map";
	private String settingsLayer1_5 	= "Back";
	
	// AvatarLayer
	private int avatarLayerIndex;
	private int avatarLayerSize;
	private int avatar_space 			= 80;
	private int avatar_xOffset 			= 500;
	private int avatar_xOffset2 		= 550;
	private int avatar_yOffset 			= 690;
	private int actualPlayer1;
	private int actualPlayer2;
	private int actualPlayer3;
	private int actualPlayer4;
	private String avatarLayer_0 		= "Back";
	private String avatarLayer_1 		= "Player 1";
	private String avatarLayer_2 		= "Player 2";
	private String avatarLayer_3 		= "Player 3";
	private String avatarLayer_4 		= "Player 4";
	
	
	public MainMenu(GameRoundConfig gameRoundConfig, ArrayList<MapConfig> mapConfigs, 
			ArrayList<PlayerConfig> playerConfigs, ArrayList<InputConfiguration> inputConfigs) {
		this.gameRoundConfig 	= gameRoundConfig;
		this.mapConfigs 		= mapConfigs;
		this.playerConfigs 		= playerConfigs;
		this.inputConfigs 		= inputConfigs;
		this.actualInputConfigs	= (ArrayList<InputConfiguration>) gameRoundConfig.getCurrentInputConfigs().clone();
		this.maxPlayers			= inputConfigs.size() > GameSettings.MAX_PLAYERS ? GameSettings.MAX_PLAYERS : inputConfigs.size();
		this.controlsLayerSize	= this.maxPlayers + 2;
		this.canvasWidth 		= AppGameContainerFSCustom.GAME_CANVAS_WIDTH;
		this.canvasHeight 		= AppGameContainerFSCustom.GAME_CANVAS_HEIGHT;
		try {
			this.avatar_back		= new Image(avatarBackgroundPath);
			this.key_wasd			= new Image(key_wasd_path);
			this.key_arrow			= new Image(key_arrow_path);
			this.pad_1				= new Image(pad_1_path);
			this.pad_2				= new Image(pad_2_path);
			this.map_controls		= new Image(map_controls_path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		loadSound();
	}
	
	private void loadSound() {
		try {
			this.successSound 	= new Sound(successSoundPath);
			this.errorSound		= new Sound(errorSoundPath);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void init() {
		reset();	
		initSettings();
	}
	
	public void reset() {
		resetMainLayer();
		resetExitLayer();
		resetSettingsLayer1();
		resetAvatarLayer();
		actualAction 	= Action.NO_ACTION;
		actualLayer 	= Layer.MAIN_LAYER;
	}

	private void initSettings() {
		timeLimit 	= gameRoundConfig.getTimeLimit();
		players		= gameRoundConfig.getCurrentPlayerConfigs().size();
		
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
	
	private void initControlsLayer() {
		
		player1_index = inputConfigs.indexOf(actualInputConfigs.get(0));
		p1_sic = player1_index;
		
		player2_index = inputConfigs.indexOf(actualInputConfigs.get(1));
		p2_sic = player2_index;
		
		if (maxPlayers > 2) {
			player3_index = inputConfigs.indexOf(actualInputConfigs.get(2));
			p3_sic = player3_index;
		}
		
		if (maxPlayers > 3) {
			player4_index = inputConfigs.indexOf(actualInputConfigs.get(3));
			p4_sic = player4_index;
		}
	}
	
	private boolean checkControls() {
		boolean bool = true;
		
		p1_error = p2_error = p3_error = p4_error = false;
		
		if (player1_index == player2_index) {
			p1_error = p2_error = true;
			bool = false;
		}
		
		if (maxPlayers > 2) {
			if (player1_index == player3_index) {
				p1_error = p3_error = true;
				bool = false;
			}
			if (player2_index == player3_index) {
				p3_error = p2_error = true;
				bool = false;
			}
		}

		if (maxPlayers > 3) {
			if (player1_index == player4_index) {
				p1_error = p4_error = true;
				bool = false;
			}

			if (player2_index == player4_index) {
				p4_error = p2_error = true;
				bool = false;
			}
			if (player3_index == player4_index) {
				p3_error = p4_error = true;
				bool = false;
			}
		}
	
		return bool;
	}
	
	private void saveControls() {

		actualInputConfigs.set(0, inputConfigs.get(player1_index));
		p1_sic = player1_index;
		actualInputConfigs.set(1, inputConfigs.get(player2_index));
		p2_sic = player2_index;
		if (maxPlayers > 2) {
			actualInputConfigs.set(2, inputConfigs.get(player3_index));
			p3_sic = player3_index;
		}
		if (maxPlayers > 3) {
			actualInputConfigs.set(3, inputConfigs.get(player4_index));
			p4_sic = player4_index;
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
			case AVATAR_LAYER:
				renderAvatarLayer(container, game, graphics);
				break;
			
			default: break;
		}		
	}
	
	private void renderAvatarLayer(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		int xPaddingBack = 320;
		int yPadding = 150;
		int fontSpace = 40;
		
		fontOutline2.drawString(180, 5, "Avatar Selection");
		
		if (avatarLayerIndex >= 1 && avatarLayerIndex <= 4) {
			
			PlayerConfig config = null;
			
			switch(avatarLayerIndex) {
				case 1: 
					config = actualPlayerConfigs.get(0);
					break;
				case 2: 
					config = actualPlayerConfigs.get(1);
					break;
				case 3: 
					config = actualPlayerConfigs.get(2);
					break;
				case 4: 
					config = actualPlayerConfigs.get(3);
					break;
			}
			
			graphics.drawImage(avatar_back, xPaddingBack, yPadding, new Color(1f, 1f, 1f, 0.3f));
			
			if (config != null) {
				graphics.drawImage(config.getImage(), xPaddingBack  + 35, yPadding  + 35);
				Color tmp = graphics.getColor();
				graphics.setColor(Color.black);
				graphics.drawRect(xPaddingBack  + 35, yPadding  + 35, config.getImage().getWidth(), config.getImage().getHeight());
				graphics.setColor(tmp);
				
				String speed = "";
				if (config.getInitialSpeedUp() == 0) {
					speed = "slow";
				}
				else if (config.getInitialSpeedUp() == 1) {
					speed = "default";
				}
				else if (config.getInitialSpeedUp() >= 2) {
					speed = "fast";
				}
	
				float scale = 0.5f;
				graphics.scale(scale, scale);
				fontOutline2.drawString((xPaddingBack + 330) /scale , (yPadding  + 35) /scale, config.getName());
				fontOutline2.drawString((xPaddingBack + 330) /scale , (yPadding  + 35 + fontSpace * 2) /scale, "Speed: " + speed);
				fontOutline2.drawString((xPaddingBack + 330) /scale , (yPadding  + 35 + fontSpace * 3) /scale, "Bombs: " + config.getInitialBombLimit());
				fontOutline2.drawString((xPaddingBack + 330) /scale , (yPadding  + 35 + fontSpace * 4) /scale, "Range: " + config.getInitialBombRange());
				graphics.resetTransform();
			}
		}
		
		graphics.scale(0.7f, 0.7f);
		
		if (avatarLayerIndex == 0) {
			fontOutline.drawString(avatar_xOffset, avatar_yOffset, avatarLayer_0);
		}
		else {
			font.drawString(avatar_xOffset, avatar_yOffset, avatarLayer_0);
		}
		
		for (int i = 0; i < actualPlayerConfigs.size(); i++) {
			
			PlayerConfig p = actualPlayerConfigs.get(i);
			if (i == 0) {
				if (avatarLayerIndex == 1) {
					fontOutline.drawString(avatar_xOffset, avatar_yOffset + avatar_space * (i + 1), avatarLayer_1);
					fontOutline.drawString(avatar_xOffset2 + (canvasWidth - fontOutline.getWidth("<" + p.getName() + ">")) / 2,
							avatar_yOffset + avatar_space * (i + 1), "<" + p.getName() + ">");
				}
				else {
					font.drawString(avatar_xOffset, avatar_yOffset + avatar_space * (i + 1), avatarLayer_1);
					font.drawString(avatar_xOffset2 + (canvasWidth - font.getWidth("<" + p.getName() + ">")) / 2,
							avatar_yOffset + avatar_space * (i + 1), "<" + p.getName() + ">");
				}
			}
			if (i == 1) {
				if (avatarLayerIndex == 2) {
					fontOutline.drawString(avatar_xOffset, avatar_yOffset + avatar_space * (i + 1), avatarLayer_2);
					fontOutline.drawString(avatar_xOffset2 + (canvasWidth - fontOutline.getWidth("<" + p.getName() + ">")) / 2,
							avatar_yOffset + avatar_space * (i + 1), "<" + p.getName() + ">");
				}
				else {
					font.drawString(avatar_xOffset, avatar_yOffset + avatar_space * (i + 1), avatarLayer_2);
					font.drawString(avatar_xOffset2 + (canvasWidth - font.getWidth("<" + p.getName() + ">")) / 2,
							avatar_yOffset + avatar_space * (i + 1), "<" + p.getName() + ">");
				}
			}
			if (i == 2) {
				if (avatarLayerIndex == 3) {
					fontOutline.drawString(avatar_xOffset, avatar_yOffset + avatar_space * (i + 1), avatarLayer_3);
					fontOutline.drawString(avatar_xOffset2 + (canvasWidth - fontOutline.getWidth("<" + p.getName() + ">")) / 2,
							avatar_yOffset + avatar_space * (i + 1), "<" + p.getName() + ">");
				}
				else {
					font.drawString(avatar_xOffset, avatar_yOffset + avatar_space * (i + 1), avatarLayer_3);
					font.drawString(avatar_xOffset2 + (canvasWidth - font.getWidth("<" + p.getName() + ">")) / 2,
							avatar_yOffset + avatar_space * (i + 1), "<" + p.getName() + ">");
				}
			}
			if (i == 3) {
				if (avatarLayerIndex == 4) {
					fontOutline.drawString(avatar_xOffset, avatar_yOffset + avatar_space * (i + 1), avatarLayer_4);
					fontOutline.drawString(avatar_xOffset2 + (canvasWidth - fontOutline.getWidth("<" + p.getName() + ">")) / 2,
							avatar_yOffset + avatar_space * (i + 1), "<" + p.getName() + ">");
				}
				else {
					font.drawString(avatar_xOffset, avatar_yOffset + avatar_space * (i + 1), avatarLayer_4);
					font.drawString(avatar_xOffset2 + (canvasWidth - font.getWidth("<" + p.getName() + ">")) / 2,
							avatar_yOffset + avatar_space * (i + 1), "<" + p.getName() + ">");
				}
			}
		}
		
		graphics.resetTransform();
	}

	private void renderSettingsLayer1(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		int xPaddingMap = 350;
		int xPaddingPlayers = 670;
		int yPadding = 170;
		int playersSpace = 10;

		int yPaddingStart=-85, xPaddingStart=0;
		
//		Image backScaled = avatar_back.getScaledCopy(880, 335);
//		backScaled.draw((this.canvasWidth - backScaled.getWidth()) /2, 10, new Color(1f, 1f, 1f, 0.3f));
		
		float scale = 0.7f;
		
		mapConfigs.get(actualMap).getImage().draw(xPaddingMap, yPadding + playersSpace / 2, 1.0f);
		Color tmp = graphics.getColor();
		graphics.setColor(Color.black);
		graphics.drawRect(xPaddingMap, yPadding + playersSpace / 2, mapConfigs.get(actualMap).getImage().getWidth(), mapConfigs.get(actualMap).getImage().getHeight());
		graphics.setColor(tmp);
		fontOutline2.drawString(xPaddingMap - BombermanGame.JAMES_FONT.getWidth("Map") / 2, yPadding - fontOutline2.getHeight("Map") / 2, "Map");
		
		for (int i = 0; i < actualPlayerConfigs.size(); i++) {
			
			PlayerConfig p = actualPlayerConfigs.get(i);
			if (i == 0) {
				p.getImage().draw(xPaddingPlayers, yPadding, 0.5f);
				tmp = graphics.getColor();
				graphics.setColor(Color.black);
				graphics.drawRect(xPaddingPlayers, yPadding, p.getImage().getWidth() * 0.5f, p.getImage().getHeight() * 0.5f);
				graphics.setColor(tmp);
				graphics.scale(scale, scale);
				fontOutline2.drawString((xPaddingPlayers - fontOutline2.getWidth("P1") * scale / 2) / scale , 
						(yPadding - fontOutline2.getHeight("P1") * scale / 2) / scale, "P1");
				graphics.resetTransform();
			}
			if (i == 1) {
				p.getImage().draw(xPaddingPlayers + p.getImage().getScaledCopy(0.5f).getWidth() + playersSpace, yPadding, 0.5f);
				tmp = graphics.getColor();
				graphics.setColor(Color.black);
				graphics.drawRect(xPaddingPlayers + p.getImage().getScaledCopy(0.5f).getWidth() + playersSpace, 
						yPadding, p.getImage().getWidth() * 0.5f, p.getImage().getHeight() * 0.5f);
				graphics.setColor(tmp);
				graphics.scale(scale, scale);
				fontOutline2.drawString((xPaddingPlayers + p.getImage().getScaledCopy(0.5f).getWidth() + p.getImage().getScaledCopy(0.5f).getWidth() + playersSpace - fontOutline2.getWidth("P2") * scale / 2) / scale , 
						(yPadding - fontOutline2.getHeight("P2") * scale / 2 ) / scale, "P2");
				graphics.resetTransform();
			}
			if (i == 2) {
				p.getImage().draw(xPaddingPlayers, yPadding + p.getImage().getScaledCopy(0.5f).getHeight() + playersSpace, 0.5f);
				tmp = graphics.getColor();
				graphics.setColor(Color.black);
				graphics.drawRect(xPaddingPlayers, yPadding + p.getImage().getScaledCopy(0.5f).getHeight() + playersSpace, 
						p.getImage().getWidth() * 0.5f, p.getImage().getHeight() * 0.5f);
				graphics.setColor(tmp);
				graphics.scale(scale, scale);
				fontOutline2.drawString((xPaddingPlayers - fontOutline2.getWidth("P1") * scale / 2) / scale , 
						(yPadding + p.getImage().getScaledCopy(0.5f).getHeight() + playersSpace - fontOutline2.getHeight("P3") * scale / 2) / scale, "P3");
				graphics.resetTransform();
			}
			if (i == 3) {
				p.getImage().draw(xPaddingPlayers + p.getImage().getScaledCopy(0.5f).getWidth() + playersSpace, 
						yPadding + p.getImage().getScaledCopy(0.5f).getHeight() + playersSpace, 0.5f);
				tmp = graphics.getColor();
				graphics.setColor(Color.black);
				graphics.drawRect(xPaddingPlayers + p.getImage().getScaledCopy(0.5f).getWidth() + playersSpace, 
						yPadding + p.getImage().getScaledCopy(0.5f).getHeight() + playersSpace, 
						p.getImage().getWidth() * 0.5f, p.getImage().getHeight() * 0.5f);
				graphics.setColor(tmp);
				graphics.scale(scale, scale);
				fontOutline2.drawString((xPaddingPlayers + p.getImage().getScaledCopy(0.5f).getWidth() + p.getImage().getScaledCopy(0.5f).getWidth() + playersSpace - fontOutline2.getWidth("P4") * scale / 2) / scale , 
						(yPadding + p.getImage().getScaledCopy(0.5f).getHeight() + playersSpace - fontOutline2.getHeight("P4") * scale / 2 ) / scale, "P4");
				graphics.resetTransform();
			}
			
		}
		graphics.scale(0.7f, 0.7f);
		if (settingsLayer1Index == 0) {
			fontOutline.drawString(settings_xOffset+xPaddingStart, settings_yOffset+yPaddingStart, settingsLayer1_0);
		}
		else {
			font.drawString(settings_xOffset+xPaddingStart, settings_yOffset+yPaddingStart, settingsLayer1_0);
		}
		

		if (settingsLayer1Index == 1) {
			fontOutline.drawString(settings_xOffset, settings_yOffset, settingsLayer1_1);
			if (timeLimit == 0) {
				fontOutline.drawString(settings_xOffset2 + (canvasWidth - fontOutline.getWidth("<" + "No Limit" + ">")) / 2, 
						settings_yOffset, "<" + "No Limit" + ">");
			}
			else {
				fontOutline.drawString(settings_xOffset2 + (canvasWidth - fontOutline.getWidth("<" + timeLimit + " Min" + ">")) / 2, 
						settings_yOffset, "<" + timeLimit + " Min" + ">");
			}

		}
		else {
			font.drawString(settings_xOffset, settings_yOffset, settingsLayer1_1);
			if (timeLimit == 0) {
				font.drawString(settings_xOffset2 + (canvasWidth - font.getWidth("No Limit")) / 2,
						settings_yOffset, "No Limit");
			}
			else {
				font.drawString(settings_xOffset2 + (canvasWidth - font.getWidth("" + timeLimit + " Min")) / 2,
						settings_yOffset, "" + timeLimit + " Min");
			}
		}
		if (settingsLayer1Index == 2) {
			fontOutline.drawString(settings_xOffset, settings_yOffset + settings_space, settingsLayer1_2);
			fontOutline.drawString(settings_xOffset2 + (canvasWidth - fontOutline.getWidth("<" + players + ">")) / 2,
					settings_yOffset + settings_space, "<" + players + ">");
		}
		else {
			font.drawString(settings_xOffset, settings_yOffset + settings_space, settingsLayer1_2);
			font.drawString(settings_xOffset2 + (canvasWidth - font.getWidth("" + players)) / 2, 
					settings_yOffset + settings_space, "" + players);
		}
		if (settingsLayer1Index == 3) {
			fontOutline.drawString(settings_xOffset, settings_yOffset + 2 * settings_space, settingsLayer1_3);
		}
		else {
			font.drawString(settings_xOffset, settings_yOffset + 2 * settings_space, settingsLayer1_3);
		}
		if (settingsLayer1Index == 4) {
			fontOutline.drawString(settings_xOffset, settings_yOffset + 3 * settings_space, settingsLayer1_4);
			fontOutline.drawString(settings_xOffset2 + (canvasWidth - fontOutline.getWidth("<" + mapConfigs.get(actualMap).getName() + ">")) / 2, 
					settings_yOffset + 3 * settings_space, "<" + mapConfigs.get(actualMap).getName() + ">");
		}
		else {
			font.drawString(settings_xOffset, settings_yOffset + 3 * settings_space, settingsLayer1_4);
			font.drawString(settings_xOffset2 + (canvasWidth - font.getWidth("" + mapConfigs.get(actualMap).getName())) / 2, 
					settings_yOffset + 3 * settings_space, "" + mapConfigs.get(actualMap).getName());
		}
		if (settingsLayer1Index == 5) {
			fontOutline.drawString(settings_xOffset, settings_yOffset + 4 * settings_space, settingsLayer1_5);
		}
		else {
			font.drawString(settings_xOffset, settings_yOffset + 4 * settings_space, settingsLayer1_5);
		}
		
		graphics.resetTransform();
	}

	private void renderControlsLayer(GameContainer container, StateBasedGame game, Graphics graphics) {
		fontOutline2.drawString(180, 5, "Controls");
		
		map_controls.draw((canvasWidth - map_controls.getWidth()) / 2,
				(canvasHeight - map_controls.getHeight()) / 2,
					new Color(1f, 1f, 1f, 0.8f));		
		
		int space = 70;
		int startY = 255;
		graphics.scale(0.7f, 0.7f);
		
		if (controlsLayerIndex == 0) {
			fontOutline.drawString((canvasWidth - fontOutline.getWidth(controlsLayer_0)) / 2 /0.7f + 30, 
					startY /0.7f, controlsLayer_0);
		}
		else {
			font.drawString((canvasWidth - font.getWidth(controlsLayer_0)) / 2 /0.7f + 30, 
					startY /0.7f, controlsLayer_0);
		}

		if (checkControls()) {
			if (controlsLayerIndex == 1) {
				fontOutline.drawString((canvasWidth - fontOutline.getWidth(controlsLayer_1)) / 2 /0.7f + 30, 
						(startY + space) /0.7f, controlsLayer_1);
			}
			else {
				font.drawString((canvasWidth - font.getWidth(controlsLayer_1)) / 2 /0.7f + 30, 
						(startY + space) /0.7f, controlsLayer_1);
			}
		}
		else {
			if (controlsLayerIndex == 1) {
				fontOutline.drawString((canvasWidth - fontOutline.getWidth(controlsLayer_1)) / 2 /0.7f + 30, 
						(startY + space) /0.7f, controlsLayer_1, new Color(1f, 0.5f, 0.5f, 1f));
			}
			else {
				font.drawString((canvasWidth - font.getWidth(controlsLayer_1)) / 2 /0.7f + 30, 
						(startY + space) /0.7f, controlsLayer_1, new Color(1f, 0.5f, 0.5f, 1f));
			}
		}

		
		if (controlsLayerIndex == 2) {
			fontOutline.drawString((canvasWidth - fontOutline.getWidth("<" + controlsLayer_2 + ">")) / 2 /0.7f + 77, 
					(startY + 2 * space) /0.7f, "<" + controlsLayer_2 + ">");
		}
		else {
			font.drawString((canvasWidth - font.getWidth(controlsLayer_2)) / 2 /0.7f + 62, 
					(startY + 2 * space) /0.7f, controlsLayer_2);
		}

		
		if (controlsLayerIndex == 3) {
			fontOutline.drawString((canvasWidth - fontOutline.getWidth("<" + controlsLayer_3 + ">")) / 2 /0.7f + 80, 
					(startY + 3 * space) /0.7f, "<" + controlsLayer_3 + ">");
		}
		else {
			font.drawString((canvasWidth - font.getWidth(controlsLayer_3)) / 2 /0.7f + 65, 
					(startY + 3 * space) /0.7f, controlsLayer_3);
		}

		if (maxPlayers > 2) {
			if (controlsLayerIndex == 4) {
				fontOutline.drawString((canvasWidth - fontOutline.getWidth("<" + controlsLayer_4 + ">")) / 2 /0.7f + 80, 
						(startY + 4 * space) /0.7f, "<" + controlsLayer_4 + ">");
			}
			else {
				font.drawString((canvasWidth - font.getWidth(controlsLayer_4)) / 2 /0.7f + 65, 
						(startY + 4 * space) /0.7f, controlsLayer_4);
			}
		}

		if (maxPlayers > 3) {
			if (controlsLayerIndex == 5) {
				fontOutline.drawString((canvasWidth - fontOutline.getWidth("<" + controlsLayer_5 + ">")) / 2 /0.7f + 80, 
						(startY + 5 * space) /0.7f, "<" + controlsLayer_5 + ">");
			}
			else {
				font.drawString((canvasWidth - font.getWidth(controlsLayer_5)) / 2 /0.7f + 65, 
						(startY + 5 * space) /0.7f, controlsLayer_5);
			}
		}
		
		graphics.resetTransform();
		
		fontOutline2.drawString(250, 120, "P1");
		if (p1_error) {
			inputConfigs.get(player1_index).getImage().draw(220, 220, new Color(1f, 0.5f, 0.5f, 0.8f));
		}
		else {
			inputConfigs.get(player1_index).getImage().draw(220, 220, new Color(0.5f, 1f, 0.5f, 0.8f));
		}
		
		fontOutline2.drawString(970, 120, "P2");
		if (p2_error) {
			inputConfigs.get(player2_index).getImage().draw(940, 220, new Color(1f, 0.5f, 0.5f, 0.8f));
		}
		else {
			inputConfigs.get(player2_index).getImage().draw(940, 220, new Color(0.5f, 1f, 0.5f, 0.8f));
		}
		
		if (maxPlayers > 2) {
			fontOutline2.drawString(250, 700, "P3");
			if (p3_error) {
				inputConfigs.get(player3_index).getImage().draw(220, 600, new Color(1f, 0.5f, 0.5f, 0.8f));
			}
			else {
				inputConfigs.get(player3_index).getImage().draw(220, 600, new Color(0.5f, 1f, 0.5f, 0.8f));
			}
		}
		
		if (maxPlayers > 3) {
			fontOutline2.drawString(970, 700, "P4");
			if (p4_error) {
				inputConfigs.get(player4_index).getImage().draw(940, 600, new Color(1f, 0.5f, 0.5f, 0.8f));
			}
			else {
				inputConfigs.get(player4_index).getImage().draw(940, 600, new Color(0.5f, 1f, 0.5f, 0.8f));
			}
		}
		
//		fontOutline.drawString(0, 0, "" + player1_index);
//		fontOutline.drawString(0, 60, "" + player2_index);
//		fontOutline.drawString(0, 120, "" + player3_index);
//		fontOutline.drawString(0, 180, "" + player4_index);
	}

	private void renderExitLayer(GameContainer container, StateBasedGame game, Graphics graphics) {
		
		fontOutline2.drawString((canvasWidth - font.getWidth(exitLayerHeading)) / 2, 450, exitLayerHeading);
		
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
		
		switch (actualLayer) {
			case CONTROLS_LAYER: 
				checkControls(); 
				break;
				
			default: break;
		}
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
			case AVATAR_LAYER:
				avatarLayer_checkInput(input);
				break;
			
			default: break;
		}
	}

	private void avatarLayer_checkInput(Input input) {
		if (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {
			avatarLayerSize = actualPlayerConfigs.size() + 1;
			if (avatarLayerIndex - 1 >= 0) {
				avatarLayerIndex--;
			}
		}
		if (input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)) {
			avatarLayerSize = actualPlayerConfigs.size() + 1;
			if (avatarLayerIndex + 1 < avatarLayerSize) {
				avatarLayerIndex++;
			}
		}
		if (input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)) {
			
			switch (avatarLayerIndex) {
			
				case 1: 
					actualPlayer1 = playerConfigs.indexOf(actualPlayerConfigs.get(0));
					if (actualPlayer1 - 1 >= 0) {
						actualPlayer1--;
						actualPlayerConfigs.set(0, playerConfigs.get(actualPlayer1));
					}
					break;
				case 2: 
					actualPlayer2 = playerConfigs.indexOf(actualPlayerConfigs.get(1));
					if (actualPlayer2 - 1 >= 0) {
						actualPlayer2--;
						actualPlayerConfigs.set(1, playerConfigs.get(actualPlayer2));
					}
					break;
				case 3: 
					actualPlayer3 = playerConfigs.indexOf(actualPlayerConfigs.get(2));
					if (actualPlayer3 - 1 >= 0) {
						actualPlayer3--;
						actualPlayerConfigs.set(2, playerConfigs.get(actualPlayer3));
					}
					break;
				case 4: 
					actualPlayer4 = playerConfigs.indexOf(actualPlayerConfigs.get(3));
					if (actualPlayer4 - 1 >= 0) {
						actualPlayer4--;
						actualPlayerConfigs.set(3, playerConfigs.get(actualPlayer4));
					}
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)) {
			
			switch (avatarLayerIndex) {
			
			case 1: 
				actualPlayer1 = playerConfigs.indexOf(actualPlayerConfigs.get(0));
				if (actualPlayer1 + 1 < playerConfigs.size()) {
					actualPlayer1++;
					actualPlayerConfigs.set(0, playerConfigs.get(actualPlayer1));
				}
				break;
			case 2: 
				actualPlayer2 = playerConfigs.indexOf(actualPlayerConfigs.get(1));
				if (actualPlayer2 + 1 < playerConfigs.size()) {
					actualPlayer2++;
					actualPlayerConfigs.set(1, playerConfigs.get(actualPlayer2));
				}
				break;
			case 3: 
				actualPlayer3 = playerConfigs.indexOf(actualPlayerConfigs.get(2));
				if (actualPlayer3 + 1 < playerConfigs.size()) {
					actualPlayer3++;
					actualPlayerConfigs.set(2, playerConfigs.get(actualPlayer3));
				}
				break;
			case 4: 
				actualPlayer4 = playerConfigs.indexOf(actualPlayerConfigs.get(3));
				if (actualPlayer4 + 1 < playerConfigs.size()) {
					actualPlayer4++;
					actualPlayerConfigs.set(3, playerConfigs.get(actualPlayer4));
				}
				break;
		}
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch (avatarLayerIndex) {
				case 0: 
					actualLayer = Layer.SETTINGS_LAYER_1;
					resetAvatarLayer();
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualLayer = Layer.SETTINGS_LAYER_1;
			input.clearKeyPressedRecord();
			resetAvatarLayer();
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
					input.clearKeyPressedRecord();
					break;
				case 3: 
					actualLayer = Layer.AVATAR_LAYER;
					input.clearKeyPressedRecord();
					break;
				case 5: 
					actualLayer = Layer.MAIN_LAYER;
					input.clearKeyPressedRecord();
					resetSettingsLayer1();
					break;
			
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualLayer = Layer.MAIN_LAYER;
			input.clearKeyPressedRecord();
			resetSettingsLayer1();
		}
		
	}

	private void controlsLayer_checkInput(Input input) {
		
		if (input.isKeyPressed(Input.KEY_W) || input.isKeyPressed(Input.KEY_UP)) {
			if (controlsLayerIndex - 1 >= 0) {
				controlsLayerIndex--;
			}	
		}
		if (input.isKeyPressed(Input.KEY_S) || input.isKeyPressed(Input.KEY_DOWN)) {
			if (controlsLayerIndex + 1 < controlsLayerSize) {
				controlsLayerIndex++;
			}
		}
		if (input.isKeyPressed(Input.KEY_A) || input.isKeyPressed(Input.KEY_LEFT)) {
			switch (controlsLayerIndex) {
				case 2: 
					if (player1_index - 1 >= 0) {
						player1_index--;
					}
					break;
				case 3: 
					if (player2_index - 1 >= 0) {
						player2_index--;
					}
					break;
				case 4: 
					if (player3_index - 1 >= 0) {
						player3_index--;
					}
					break;
				case 5: 
					if (player4_index - 1 >= 0) {
						player4_index--;
					}
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_D) || input.isKeyPressed(Input.KEY_RIGHT)) {
			switch (controlsLayerIndex) {
			case 2: 
				if (player1_index + 1 < inputConfigs.size()) {
					player1_index++;
				}
				break;
			case 3: 
				if (player2_index + 1 < inputConfigs.size()) {
					player2_index++;
				}
				break;
			case 4: 
				if (player3_index + 1 < inputConfigs.size()) {
					player3_index++;
				}
				break;
			case 5: 
				if (player4_index + 1 < inputConfigs.size()) {
					player4_index++;
				}
				break;
		}
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			
			switch (controlsLayerIndex) {
				case 0: 
					actualLayer = Layer.MAIN_LAYER;
					input.clearKeyPressedRecord();
					resetControlsLayer();
					break;
				
				case 1: 
					if (checkControls()) {
						if (successSound.playing()) {
							successSound.stop();
						}
						successSound.play();
						saveControls();
					}
					else {
						if (errorSound.playing()) {
							errorSound.stop();
						}
						errorSound.play();
					}
					break;
			}
			
			
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualLayer = Layer.MAIN_LAYER;
			input.clearKeyPressedRecord();
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
					input.clearKeyPressedRecord();
					break;
				case 1: 
					actualLayer = Layer.MAIN_LAYER;
					input.clearKeyPressedRecord();
					resetExitLayer();
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualLayer = Layer.MAIN_LAYER;
			input.clearKeyPressedRecord();
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
					input.clearKeyPressedRecord();
					break;
				case 1: 
					actualLayer = Layer.CONTROLS_LAYER;
					initControlsLayer();
					input.clearKeyPressedRecord();
					break;
				case 2: 
					actualLayer = Layer.EXIT_LAYER;
					input.clearKeyPressedRecord();
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			actualLayer = Layer.EXIT_LAYER;
			input.clearKeyPressedRecord();
		}
	}
	
	private void resetAvatarLayer() {
		avatarLayerIndex = 0;
	}
	
	private void resetSettingsLayer1() {
		settingsLayer1Index = 0;
	}

	private void resetControlsLayer() {
		controlsLayerIndex = 0;		
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
		gameRoundConfig.setCurrentInputConfigs(actualInputConfigs);
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
