var dataModel={
	dataArr: [],
	add: function (id, email, password, message){
		console.log("add....dataModel: " + message);
		this.dataArr.push({
			"id": id, "email": email, "password": password, "message": message
		});
	},
	
	remove: function (id){
		console.log(this.dataArr.length);
		var idx = -1;
		for (var i = 0, len = this.dataArr.length; i < len; i++){
			console.log(this.dataArr[i].id + ":" + id);
			
			if (this.dataArr[i].id == id){
				idx = i;
				break;
			}
		}
		
		if (idx > -1){
			this.dataArr.splice(idx, 1);
		}
	},
	
	removeByIndex: function(idx){
		this.dataArr.splice(idx, 1);
	},
	
	findAll: function(){
		return this.dataArr.slice(0);
	},
	
	findByIndex: function(idx){
		return this.dataArr[idx];
	}
};