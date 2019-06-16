package database.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class ProdBatchDTO implements Serializable, IProdBatchDTO {
	private int prodBatchId;
	private int userId;
	private int recipeId;
	private Date date;
	private List<Integer> matList;
	private List<Integer> labList;
	private List<Double> nettoList;
	private List<Double> taraList;

	@Override
	public int getProdBatchId(){
		return prodBatchId;
	}
	@Override
	public void setProdBatchId(int prodBatchId){
		this.prodBatchId = prodBatchId;
	}
	@Override
	public int getUserId(){
		return userId;
	}
	@Override
	public void setUserId(int userId){
		this.userId = userId;
	}
	@Override
	public int getRecipeId(){
		return recipeId;
	}
	@Override
	public void setRecipeId(int recipeId){
		this.recipeId = recipeId;
	}
	@Override
	public Date getDate() {
		return date;
	}
	@Override
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public List<Integer> getMatList(){
		return matList;
	}
	@Override
	public void setMatList(List<Integer> matList) {
		this.matList = matList;
	}
	@Override
	public List<Integer> getLabList(){
		return labList;
	}
	@Override
	public void setLabList(List<Integer> labList) {
		this.labList = labList;
	}

	@Override
	public List<Double> getNettoList() {
		return nettoList;
	}

	@Override
	public void setNettoList(List<Double> nettoList ) {
		this.nettoList = nettoList;
	}

	@Override
	public List<Double> getTaraList() {
		return taraList;
	}

	@Override
	public void setTaraList(List<Double> taraList) {
		this.taraList = taraList;
	}

	@Override
	public String toString(){
//		String out = "" + prodBatchId;
//		out +=  userId;
//		out += recipeId;
//		out += date.toString();
//		out += matList.toString();
//		out += labList.toString();
		return "" + prodBatchId + userId + recipeId + date.toString() + matList.toString() + labList.toString();
	}
}
