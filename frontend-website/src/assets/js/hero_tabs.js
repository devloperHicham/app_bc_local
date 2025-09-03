
    		var cTASecondaryContainer = document.getElementById("cTASecondaryContainer");
    		if(cTASecondaryContainer) {
      			cTASecondaryContainer.addEventListener("click", function (e) {
        				// Add your code here
      			});
    		}
    		
    		var tableItemContainer1 = document.getElementById("tableItemContainer1");
    		if(tableItemContainer1) {
      			tableItemContainer1.addEventListener("click", function () {
        				var popup = document.getElementById("quoteDetailsContainer");
        				if(!popup) return;
        				var popupStyle = popup.style;
        				if(popupStyle) {
          					popupStyle.display = "flex";
          					popupStyle.zIndex = 100;
          					popupStyle.backgroundColor = "rgba(5, 37, 110, 0.16)";
          					popupStyle.alignItems = "center";
          					popupStyle.justifyContent = "center";
        				}
        				popup.setAttribute("closable", "");
        				
        				var onClick = popup.onClick || function(e) {
          					if(e.target === popup && popup.hasAttribute("closable")) {
            						popupStyle.display = "none";
          					}
        				};
        				popup.addEventListener("click", onClick);
      			});
    		}