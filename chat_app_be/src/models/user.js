import mongoose from 'mongoose'
const Schema = mongoose.Schema;

const userSchema = new Schema({
	_id: Schema.Types.ObjectId,
	username: String,
	password: String,
	createdAt: Date,
	lastLogin: Date
});

module.exports = mongoose.model( 'User', userSchema );