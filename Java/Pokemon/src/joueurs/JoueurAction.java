package joueurs;


public class JoueurAction{
	
		public enum action{
			fight,move,capture,mort
		}
		
		/**
		 * "move" "fight" "capture" ou "mort"
		 */
		action actionType;
		
		/**
		 * nom du pokemon (fight/capture)/ movement direction "Left", "Right", "Up", "Down" (move)
		 */
		String actionDef; 
								
		
		/**
		 * Constructeur pour un action du joueur:
		 * <ul>
		 * <li>aType : il faut être "fight", "move", "capture", ou "mort"
		 * <ol>
		 * <li>move : aller à une cellule voisin</li>
		 * <li>fight (batte un pokemon sauvage dans la cellule)</li>
		 * <li>capture (essayer à capturer un pokemon sauvage dans la cellule)</li>
		 * <li>mort (si le joueur a été tué)</li>
		 * <li>ATTENTION: Si le joueur est vivant, il faut choisir fight OU capture OU move</li>
		 * <li>ATTENTION: Si il y a plus qu'un pokemon avec le meme nom dans la cellule,
		 * le premier pokemon dans la liste est choisir</li>
		 * </ol></li>
		 * <li>aDef : definition de l'action
		 * <ol>
		 * <li>"move" : direction "Left","Right","Up" ou "Down"</li>
		 * <li>"fight" et "capture" : nom du pokemon</li>
		 * <li>"mort" : n'importe quoi</li>
		 * </ol></li>
		 * </ul>
		 * @param aType le type d'action ("move", "fight", ou "cature")
		 * @param aDef la definition d'action (une direction pour "move" et le nom du pokemon sauvage 
		 * pour "fight"/"capture")
		 */
		JoueurAction(String aType, String aDef){
			actionDef = aDef;
			switch(aType){
				case "fight":actionType=action.fight;break;
				case "capture":actionType=action.capture;break;
				case "move":actionType=action.move;break;
				default : actionType=action.mort;break;
			}
		}
		
		/**
		 * @return actionType
		 */
		public action getActionType(){
			return actionType;
		}
		
		/**
		 * @return actionDef
		 */
		public String getActionDefinition(){
			return actionDef;
		}
		
		
	}
