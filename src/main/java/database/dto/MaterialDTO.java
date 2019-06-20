/*
Forfatter: Lukas
Ansvar: Klassen håndterer overførsel af data til databasen for råvareobjekter og bruger IMaterialDTO interfacet.
*/

package database.dto;

import java.io.Serializable;
import java.sql.Date;

public class MaterialDTO implements Serializable, IMaterialDTO {

	private int materialBatchId;
	private int ingredientId;
	private int userId;
	private double amount;
	private Date date;
	private boolean order;
	private String supplier;

	@Override
	public int getMaterialBatchId() {
		return materialBatchId;
	}
	@Override
	public void setMaterialBatchId(int materialBatchId) {
		this.materialBatchId = materialBatchId;
	}
	@Override
	public int getIngredientId() {
		return ingredientId;
	}
	@Override
	public void setIngredientId(int ingredientId) {
		this.ingredientId = ingredientId;
	}
	@Override
	public int getUserId() {
		return userId;
	}
	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public double getAmount() {
		return amount;
	}
	@Override
	public void setAmount(double amount) {
		this.amount = amount;
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
	public boolean getOrder() {
		return order;
	}
	@Override
	public void setOrder(boolean order) {
		this.order = order;
	}
	@Override
	public String getSupplier() {
		return supplier;
	}
	@Override
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString(){
		return "" + materialBatchId + ingredientId + userId + amount + date.toString() + order;
	}
}
