import React from "react";
import { Container, Row, Col, Card } from "react-bootstrap";
import Header from "./Header";
import {
  FaClock,
  FaCocktail,
  FaSnowflake,
  FaTshirt,
  FaUtensils,
  FaWifi,
  FaDumbbell,
  FaSwimmingPool, // New service icon
  FaCar,
  FaSpa, // New service icon
} from "react-icons/fa";

const HotelService = () => {
  return (
    <>
      <div className="mb-2">
        <Header title={"Our Services"} />

        <Row className="mt-4">
          <h4 className="text-center">
            Services at <span className="hotel-color"> lakeSide - </span>Hotel
            <span className="gap-2">
              <FaClock className="ml-5" /> 24-Hour Front Desk
            </span>
          </h4>
        </Row>
        <hr />

        <Row xs={1} md={2} lg={3} className="g-4 mt-2">
          <Col>
            <Card>
              <Card.Body>
                <Card.Title className="hotel-color">
                  <FaWifi /> WiFi
                </Card.Title>
                <Card.Text>Stay connected with high-speed internet access.</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card>
              <Card.Body>
                <Card.Title className="hotel-color">
                  <FaUtensils /> Breakfast
                </Card.Title>
                <Card.Text>Start your day with a delicious breakfast buffet.</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card>
              <Card.Body>
                <Card.Title className="hotel-color">
                  <FaTshirt /> Laundry
                </Card.Title>
                <Card.Text>Keep your clothes clean and fresh with our laundry service.</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card>
              <Card.Body>
                <Card.Title className="hotel-color">
                  <FaCocktail /> Mini-bar
                </Card.Title>
                <Card.Text>Enjoy a refreshing drink or snack from our in-room mini-bar.</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card>
              <Card.Body>
                <Card.Title className="hotel-color">
                  <FaDumbbell /> Gym
                </Card.Title>
                <Card.Text>Stay fit with our well-equipped gym facilities and professional personal trainer.</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card>
              <Card.Body>
                <Card.Title className="hotel-color">
                  <FaSnowflake /> Air conditioning
                </Card.Title>
                <Card.Text>Stay cool and comfortable with our air conditioning system.</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card>
              <Card.Body>
                <Card.Title className="hotel-color">
                  <FaSwimmingPool /> Swimming Pool
                </Card.Title>
                <Card.Text>Relax and unwind by our luxurious swimming pool.</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card>
              <Card.Body>
                <Card.Title className="hotel-color">
                  <FaCar /> Valet Parking
                </Card.Title>
                <Card.Text>Experience convenient and secure valet parking services.</Card.Text>
              </Card.Body>
            </Card>
          </Col>
          <Col>
            <Card>
              <Card.Body>
                <Card.Title className="hotel-color">
                  <FaSpa /> Spa
                </Card.Title>
                <Card.Text>Indulge in a rejuvenating spa experience with our skilled therapists.</Card.Text>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </div>
      <hr />
    </>
  );
};

export default HotelService;
