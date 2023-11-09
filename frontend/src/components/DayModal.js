import React, { Component } from "react";
 
// importing all of these classes from reactstrap module
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Form,
  FormGroup,
  Input,
  Label
} from "reactstrap";
 
// build a class base component
class DayModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeItem: this.props.activeItem
    };
  }

  handleChange = e => {
    let { name, value } = e.target;
    const activeItem = { ...this.state.activeItem, [name]: value };
    this.setState({ activeItem });
  };
 
  // rendering modal in the custommodal class received toggle and on save as props,
  render() {
    const { toggle, onSave } = this.props;
    return (
      <Modal isOpen={true} toggle={toggle}>
        <ModalHeader toggle={toggle}> Проект </ModalHeader>
        <ModalBody>
         
          <Form>
 
            {/* 3 formgroups
            1 title label */}
            <FormGroup>
              <Label for="time">Время, потраченное на проект</Label>
              <Input
                type="time"
                name="time"
                value={this.state.activeItem.time}
                onChange={this.handleChange}
                placeholder="Введите время, потраченное на проект"
              />
            </FormGroup>
 
            {/* 2 last name label */}
            <FormGroup>
              <Label for="project_id">Проект</Label>
              <Input
                type="number"
                name="project_id"
                value={this.state.activeItem.project_id}
                onChange={this.handleChange}
                placeholder="Введите id проекта"
              />
            </FormGroup>
 
            {/* 3 patronymic label */}
            <FormGroup>
              <Label for="comment">Комментарий</Label>
              <Input
                type="text"
                name="comment"
                value={this.state.activeItem.comment}
                onChange={this.handleChange}
                placeholder="Введите комментарий"
              />
            </FormGroup>
          </Form>
        </ModalBody>
        {/* create a modal footer */}
        <ModalFooter>
          <Button color="success" onClick={() => onSave(this.state.activeItem)}>
            Save
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}
export default DayModal;