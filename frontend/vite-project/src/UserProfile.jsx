const AddressCard = ({countries, ...props}) =>{


    return (
        <div>
            <h1>{countries}</h1>
            <img src={`https://randomuser.me/api/portraits/men/1.jpg`}
            />
            {props.children}
        </div>

    )
}

export default AddressCard;

