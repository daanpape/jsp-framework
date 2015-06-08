package be.howest.web.framework.controller;

public class ControllerRequest {
	private Controller controller;
	private String controllername;
	private String action;
	private int id;
	private String path;
	
	/**
	 * Construct a new ControllerRequest.
	 * @param controller the controller.
	 * @param controllername the name of the controller.
	 * @param action the action to execute.
	 * @param id the id of the resource
	 */
	public ControllerRequest(Controller controller, String controllername, String action, int id, String path) {
		this.controller = controller;
		this.controllername = controllername;
		this.action = action;
		this.id = id;
		this.path = path;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public String getControllername() {
		return controllername;
	}

	public void setControllername(String controllername) {
		this.controllername = controllername;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
