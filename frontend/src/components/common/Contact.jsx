import React from "react"
import { Container } from "react-bootstrap"

const Contact = () => {
    return (
        <div className="contact mb-5">
            <Container className="text-center px-5 py-5 justify-content-center">
                <div className="row">
                    <div className="col">
                        <h1>Contact us</h1>
                        <p className="contact">Address: 106 Hoa Lac</p>
                        <p className="contact">Tel: 0912345678</p>
                    </div>
                    <div className="col">
                        <h1>About us</h1>
                        <p>Welcome to Lakeside Hotel, a haven of tranquility on the shores of a pristine lake. Our commitment to impeccable service and attention to detail ensures a refined and comfortable retreat. Whether for a relaxing getaway or a special celebration, Lakeside Hotel invites you to experience the charm of lakeside living, where every moment is crafted for your comfort and enjoyment.
</p>
                    </div>
                </div>
            </Container>
        </div>
    )
}

export default Contact