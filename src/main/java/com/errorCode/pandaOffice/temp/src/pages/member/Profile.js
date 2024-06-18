import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {callProfileAPI} from "../../apis/MemberAPICalls";
import ProfileItem from "../../components/items/ProfileItem";

function Profile() {

    const dispatch = useDispatch();
    const { profileInfo } = useSelector(state => state.memberReducer);

    useEffect(() => {
        dispatch(callProfileAPI());
    }, []);

    return (
        <div className="profile-background-div">
            {
                profileInfo && <ProfileItem profileInfo={profileInfo}/>
            }
        </div>
    );
}

export default Profile;