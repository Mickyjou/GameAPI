package GameState;

import java.util.HashMap;
import java.util.Map;

public enum GameState {
	LOBBY,
	PREPARING,
	INGAME,
	PROTECTING,
	END;
	
	private static Map<String, GameState> gamestates = new HashMap<String, GameState>();
	
	public static void setGameState(String arena, GameState state) {
		gamestates.put(arena, state);
		}

	
	public static GameState getGameState(String arena){
		if(gamestates.get(arena) != null){
			return gamestates.get(arena);
		}else{
			return null;
		}
	}
}
