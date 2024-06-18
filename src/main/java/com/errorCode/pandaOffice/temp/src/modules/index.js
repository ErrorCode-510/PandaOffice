import {combineReducers} from "redux";
import productReducer from "./ProductModules";
import memberReducer from "./MemberModules";
import orderReducer from "./OrderModules";
import reviewReducer from "./ReviewModule";

const rootReducer = combineReducers({
    productReducer, memberReducer, orderReducer, reviewReducer
});

export default rootReducer;